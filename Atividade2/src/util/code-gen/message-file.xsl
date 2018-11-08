<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:jee="http://java.sun.com/xml/ns/javaee"

    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:exsl="http://exslt.org/common"
    xmlns:func="http://exslt.org/functions"

    exclude-result-prefixes="jee"
    extension-element-prefixes="exsl func">

<xsl:template name="faces-config">
    <xsl:variable name="facesConfigPath" select="concat($webinfFolder, '/faces-config.xml')"/>
    <xsl:variable name="facesConfig" select="document($facesConfigPath)"/>
    <exsl:document href="{$facesConfigPath}" method="xml">
        <xsl:apply-templates select="$facesConfig" mode="faces-config">
            <xsl:with-param name="params">
                <subsystem>core</subsystem>
                <basename><xsl:value-of select="concat(@organization, '.', @system, '.', @subsystem, '.messages')"/></basename>
                <var><xsl:value-of select="concat('msgs', func:uppercasefirstletter(@subsystem))"/></var>
            </xsl:with-param>
        </xsl:apply-templates>
    </exsl:document>
</xsl:template>

<xsl:template match="*|node()" mode="faces-config">
    <xsl:param name="params"/>
    <xsl:copy>
        <xsl:apply-templates mode="faces-config">
            <xsl:with-param name="params" select="$params"/>
        </xsl:apply-templates>
    </xsl:copy>
</xsl:template>

<xsl:template match="jee:resource-bundle" mode="faces-config" xmlns="http://java.sun.com/xml/ns/javaee">
    <xsl:param name="params"/>
    <xsl:copy>
        <xsl:apply-templates mode="faces-config">
            <xsl:with-param name="params" select="$params"/>
        </xsl:apply-templates>
    </xsl:copy>
    <xsl:if test="not(following-sibling::jee:resource-bundle)">
        <xsl:variable name="subsystem" select="exsl:node-set($params)/subsystem"/>
        <xsl:variable name="basename" select="exsl:node-set($params)/basename"/>
        <xsl:variable name="var" select="exsl:node-set($params)/var"/>
        <xsl:if test="not(../jee:resource-bundle/jee:var[text() = $var])">
            <xsl:copy-of select="../jee:resource-bundle[1]/preceding-sibling::text()[2]"/>
            <xsl:comment> Loads resource bundles for i18n messages and assigns names to them for <xsl:value-of select="$subsystem"/> subsystem </xsl:comment>
            <xsl:copy-of select="../jee:resource-bundle[1]/preceding-sibling::text()[1]"/>
            <resource-bundle>
                <xsl:copy-of select="../jee:resource-bundle[1]/text()[1]"/>
                <base-name><xsl:value-of select="$basename"/></base-name>
                <xsl:copy-of select="../jee:resource-bundle[1]/text()[1]"/>
                <var><xsl:value-of select="$var"/></var>
                <xsl:copy-of select="../jee:resource-bundle[1]/text()[last()]"/>
            </resource-bundle>
            <xsl:text>
            </xsl:text>
        </xsl:if>
    </xsl:if>
</xsl:template>

<xsl:template match="module" mode="message-file">
    <xsl:variable name="packageName" select="concat(@organization, '.', @system, '.', @subsystem)"/>
    <xsl:variable name="folderName" select="translate($packageName, '.', '/')"/>

    <xsl:call-template name="faces-config"/>
    <exsl:document href="{$rootFolder}/../resources/{$folderName}/messages.properties" method="text">
##
## Resource bundle for package: <xsl:value-of select="@subsystem"/>
## Language: American English
##

# Menu labels for all functionalities of the package:
core.menu.admin = Administration
<!-- --><xsl:apply-templates mode="message-file"/>
    </exsl:document>
</xsl:template>

<xsl:template name="properties">
    <xsl:param name="values"/>
    <xsl:for-each select="exsl:node-set($values)/value">
        <xsl:value-of select="concat(@key, '=', ./text(), $n)"/>
    </xsl:for-each>
</xsl:template>

<xsl:template match="class" mode="message-file">
    <xsl:variable name="entityName" select="func:lowercasefirstletter(@name)"/>
    <xsl:value-of select="concat($n,'# class ', @name, $n)"/>
    <xsl:apply-templates mode="message-file"/>
    <xsl:value-of select="$n"/>
    <xsl:call-template name="properties">

        <xsl:with-param name="values">
            <value key="{$entityName}.text.deleteSucceeded">Successfully deleted {0,choice,0#no <xsl:value-of select="$entityName"/>s|1#one <xsl:value-of select="$entityName"/>s|1&lt;{0,number,integer} <xsl:value-of select="$entityName"/>s}</value>
            <value key="{$entityName}.text.entities"><xsl:value-of select="@name"/>s</value>
            <value key="{$entityName}.text.noEntities">There is no <xsl:value-of select="$entityName"/>s registered</value>
            <value key="{$entityName}.text.noEntitiesFiltered">No <xsl:value-of select="$entityName"/> was found for this filter</value>
            <value key="{$entityName}.title">Manage <xsl:value-of select="@name"/>s</value>
            <value key="{$entityName}.title.description">Create, recovery, update and delete <xsl:value-of select="$entityName"/> data</value>
            <value key="{$entityName}.title.create">Register new <xsl:value-of select="@name"/></value>
            <value key="{$entityName}.title.create.description">Insert data for the new <xsl:value-of select="$entityName"/></value>
            <value key="{$entityName}.title.update">Update <xsl:value-of select="@name"/></value>
            <value key="{$entityName}.title.update.description">Modify data for existing <xsl:value-of select="$entityName"/></value>
            <value key="{$entityName}.title.retrieve"><xsl:value-of select="@name"/>'s Data</value>
            <value key="{$entityName}.title.retrieve.description">Display <xsl:value-of select="$entityName"/>'s data</value>
            <value key="{$entityName}.text.updateSucceeded">Update successfully finished</value>
            <value key="{$entityName}.text.updateFailed">Update failed</value>
            <value key="{$entityName}.text.createSucceeded">Register succeeded</value>
        </xsl:with-param>
    </xsl:call-template>
</xsl:template>

<xsl:template match="field" mode="message-file">
    <xsl:variable name="entityName" select="func:lowercasefirstletter(../@name)"/>
    <xsl:call-template name="properties">
        <xsl:with-param name="values">
            <value key="{$entityName}.field.{@name}"><xsl:value-of select="@name"/></value>
        </xsl:with-param>
    </xsl:call-template>
</xsl:template>

<xsl:template match="text()" mode="message-file"/>

</xsl:stylesheet>
