<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
          xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
          xmlns:exsl="http://exslt.org/common"
          xmlns:func="http://exslt.org/functions"
          extension-element-prefixes="exsl func">

<xsl:import href="view-files.xsl"/>
<xsl:import href="message-file.xsl"/>

<xsl:param name="rootFolder" select="/class-list/@root-folder"/>
<xsl:param name="resourcesFolder" select="concat($rootFolder,'/../resources')"/>
<xsl:param name="webappFolder" select="concat($rootFolder,'/../webapp')"/>
<xsl:param name="webinfFolder" select="concat($webappFolder,'/WEB-INF')"/>
<xsl:variable name="n"><xsl:text>&#xa;</xsl:text></xsl:variable>

<func:function name="func:lowercase">
    <xsl:param name="value" select="''"/>
    <xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'" />
    <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
    <func:result select="translate($value, $uppercase, $lowercase)"/>
</func:function>

<func:function name="func:lowercasefirstletter">
    <xsl:param name="value" select="''"/>
    <xsl:variable name="firstLetter" select="substring($value, 1, 1)" />
    <xsl:variable name="otherLetters" select="substring($value, 2)" />
    <func:result select="concat(func:lowercase($firstLetter), $otherLetters)"/>
</func:function>

<func:function name="func:uppercase">
    <xsl:param name="value" select="''"/>
    <xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'" />
    <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
    <func:result select="translate($value, $lowercase, $uppercase)"/>
</func:function>

<func:function name="func:uppercasefirstletter">
    <xsl:param name="value" select="''"/>
    <xsl:variable name="firstLetter" select="substring($value, 1, 1)" />
    <xsl:variable name="otherLetters" select="substring($value, 2)" />
    <func:result select="concat(func:uppercase($firstLetter), $otherLetters)"/>
</func:function>

<xsl:template name="package-name">
    <xsl:param name="moduleNode" select="ancestor::module"/>
    <xsl:param name="organization" select="$moduleNode/@organization"/>
    <xsl:param name="system" select="$moduleNode/@system"/>
    <xsl:param name="subsystem" select="$moduleNode/@subsystem"/>
    <xsl:param name="module" select="$moduleNode/@name"/>
    <xsl:value-of select="concat($organization, '.', $system, '.', $subsystem, '.', $module)"/>
</xsl:template>

<xsl:template name="class-name">
    <xsl:param name="moduleNode" select="ancestor::module"/>
    <xsl:param name="organization" select="$moduleNode/@organization"/>
    <xsl:param name="system" select="$moduleNode/@system"/>
    <xsl:param name="subsystem" select="$moduleNode/@subsystem"/>
    <xsl:param name="module" select="$moduleNode/@name"/>
    <xsl:param name="typeName" select="@name"/>
    <xsl:param name="typeNameSuffix" select="''"/>
    <xsl:value-of select="concat($organization, '.', $system, '.', $subsystem, '.', $module, '.', $typeName, $typeNameSuffix)"/>
</xsl:template>

<xsl:template name="type-file">
    <xsl:param name="moduleNode" select="ancestor::module"/>

    <xsl:param name="typeNameSuffix" select="''"/>
    <xsl:param name="typeName" select="concat(@name, $typeNameSuffix)"/>
    <xsl:param name="typeKind" select="'class'"/>
    <xsl:param name="extends" select="''"/>
    <xsl:param name="implements" select="''"/>

    <xsl:param name="organization" select="$moduleNode/@organization"/>
    <xsl:param name="system" select="$moduleNode/@system"/>
    <xsl:param name="subsystem" select="$moduleNode/@subsystem"/>
    <xsl:param name="module" select="$moduleNode/@name"/>
    <xsl:param name="packageName" select="concat($organization, '.', $system, '.', $subsystem, '.', $module)"/>
    <xsl:param name="packageFolder" select="translate($packageName,'.','/')"/>

    <xsl:param name="headerContent" select="''"/>
    <xsl:param name="typeContent" select="''"/>
    <xsl:param name="fileContent">
        <xsl:value-of select="concat('package ',$packageName,';',$n)"/>
        <xsl:value-of select="$headerContent"/>
        <xsl:text>public </xsl:text>
        <xsl:value-of select="concat($typeKind, ' ', $typeName)"/>
        <xsl:if test="$extends">
            <xsl:value-of select="concat(' extends ', $extends)"/>
        </xsl:if>
        <xsl:if test="$implements">
            <xsl:value-of select="concat(' implements ', $implements)"/>
        </xsl:if>
        <xsl:value-of select="concat(' {', $n)"/>
        <xsl:value-of select="$typeContent"/>
        <xsl:value-of select="concat('}',$n)"/>
    </xsl:param>
    <exsl:document href="{$rootFolder}/{$packageFolder}/{$typeName}.java" method="text">
        <xsl:value-of select="$fileContent"/>
    </exsl:document>
</xsl:template>

<xsl:template match="/">
    <xsl:apply-templates mode="metamodel"/>
    <xsl:apply-templates mode="persistence-interface"/>
    <xsl:apply-templates mode="persistence-jpa-implementation"/>
    <xsl:apply-templates mode="application-interface"/>
    <xsl:apply-templates mode="application-implementation"/>
    <xsl:apply-templates mode="controller-implementation"/>
    <xsl:apply-templates mode="view-files">
        <xsl:with-param name="viewroot" select="concat($rootFolder, '/../webapp')"/>
    </xsl:apply-templates>
    <xsl:apply-templates mode="message-file"/>
</xsl:template>

<xsl:template match="class" mode="metamodel">
    <xsl:call-template name="type-file">
        <xsl:with-param name="typeNameSuffix">_</xsl:with-param>
        <xsl:with-param name="typeKind">abstract class</xsl:with-param>
        <xsl:with-param name="module">metamodel</xsl:with-param>
        <xsl:with-param name="extends" select="'PersistentObjectSupport_'"/>
        <xsl:with-param name="headerContent">
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import <xsl:call-template name="class-name"/>;

@StaticMetamodel(<xsl:value-of select="@name"/>.class)
<!-- --></xsl:with-param>
        <xsl:with-param name="typeContent">
            <xsl:for-each select="field">
                <xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'" />
                <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />

                <xsl:value-of select="concat('    public static final String ',translate(@name,$lowercase,$uppercase),'=&quot;',@name,'&quot;;',$n)"/>
                <xsl:value-of select="concat('    public static volatile SingularAttribute&lt;',../@name,', ',@type,'&gt; ', @name, ';',$n)"/>
            </xsl:for-each>
<!-- --></xsl:with-param>
    </xsl:call-template>
</xsl:template>

<xsl:template match="class" mode="persistence-interface">
    <xsl:call-template name="type-file">
        <xsl:with-param name="typeNameSuffix">DAO</xsl:with-param>
        <xsl:with-param name="typeKind">interface</xsl:with-param>
        <xsl:with-param name="module">persistence</xsl:with-param>
        <xsl:with-param name="extends" select="concat('BaseDAO&lt;', @name, '&gt;')"/>
        <xsl:with-param name="headerContent">
import javax.ejb.Local;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import <xsl:call-template name="class-name"/>;

@Local
<!-- --></xsl:with-param>
    </xsl:call-template>
</xsl:template>

<xsl:template match="class" mode="persistence-jpa-implementation">
    <xsl:call-template name="type-file">
        <xsl:with-param name="typeNameSuffix">JPADAO</xsl:with-param>
        <xsl:with-param name="module">persistence</xsl:with-param>
        <xsl:with-param name="extends" select="concat('BaseJPADAO&lt;', @name, '&gt;')"/>
        <xsl:with-param name="implements" select="concat(@name, 'DAO')"/>
        <xsl:with-param name="headerContent">
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import <xsl:call-template name="class-name"/>;

@Stateless
<!-- --></xsl:with-param>
        <xsl:with-param name="typeContent">
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
<!-- --></xsl:with-param>
    </xsl:call-template>
</xsl:template>

<xsl:template match="class" mode="application-interface">
    <xsl:call-template name="type-file">
        <xsl:with-param name="typeNameSuffix">Service</xsl:with-param>
        <xsl:with-param name="typeKind">interface</xsl:with-param>
        <xsl:with-param name="module">application</xsl:with-param>
        <xsl:with-param name="extends" select="concat('CrudService&lt;', @name, '&gt;')"/>
        <xsl:with-param name="headerContent">
import javax.ejb.Local;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import <xsl:call-template name="class-name"/>;

@Local
<!-- --></xsl:with-param>
    </xsl:call-template>
</xsl:template>

<xsl:template match="class" mode="application-implementation">
    <xsl:variable name="DAOClass" select="concat(@name, 'DAO')"/>
    <xsl:variable name="daoField" select="func:lowercasefirstletter($DAOClass)"/>
    <xsl:call-template name="type-file">
        <xsl:with-param name="typeNameSuffix">ServiceBean</xsl:with-param>
        <xsl:with-param name="module">application</xsl:with-param>
        <xsl:with-param name="extends" select="concat('CrudServiceBean&lt;', @name, '&gt;')"/>
        <xsl:with-param name="implements" select="concat(@name, 'Service')"/>
        <xsl:with-param name="headerContent">
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import <xsl:call-template name="class-name"/>;
import <xsl:call-template name="class-name">
    <xsl:with-param name="module" select="'persistence'"/>
    <xsl:with-param name="typeNameSuffix" select="'DAO'"/>
</xsl:call-template>;

@Stateless @PermitAll
<!-- --></xsl:with-param>
        <xsl:with-param name="typeContent">
    private static final long serialVersionUID = 1L;

    @EJB
    private <xsl:value-of select="concat($DAOClass, ' ', $daoField)"/>;

    @Override
    public BaseDAO&lt;<xsl:value-of select="@name"/>&gt; getDAO() {
        return <xsl:value-of select="$daoField"/>;
    }
<!-- --></xsl:with-param>
    </xsl:call-template>
</xsl:template>

<xsl:template match="class" mode="controller-implementation">
    <xsl:variable name="ServiceClass" select="concat(@name, 'Service')"/>
    <xsl:variable name="serviceField" select="func:lowercasefirstletter($ServiceClass)"/>
    <xsl:call-template name="type-file">
        <xsl:with-param name="typeNameSuffix">Controller</xsl:with-param>
        <xsl:with-param name="module">controller</xsl:with-param>
        <xsl:with-param name="extends" select="concat('CrudController&lt;', @name, '&gt;')"/>
        <xsl:with-param name="headerContent">
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import <xsl:call-template name="class-name">
    <xsl:with-param name="module" select="'application'"/>
    <xsl:with-param name="typeNameSuffix" select="'Service'"/>
</xsl:call-template>;
import <xsl:call-template name="class-name"/>;

@Named @SessionScoped
<!-- --></xsl:with-param>
        <xsl:with-param name="typeContent">
    private static final long serialVersionUID = 1L;

    @EJB
    private <xsl:value-of select="concat($ServiceClass, ' ', $serviceField)"/>;

    @Override
    public CrudService&lt;<xsl:value-of select="@name"/>&gt; getCrudService() {
        return <xsl:value-of select="$serviceField"/>;
    }

    @Override
    public void initFilters() {

    }
<!-- --></xsl:with-param>
    </xsl:call-template>
</xsl:template>

</xsl:stylesheet>
