<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns="http://www.w3.org/1999/xhtml"      
    xmlns:ui="http://java.sun.com/jsf/facelets"
    xmlns:f="http://java.sun.com/jsf/core"
    xmlns:h="http://java.sun.com/jsf/html"
    xmlns:p="http://primefaces.org/ui"
    xmlns:adm="http://github.com/adminfaces"

    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:exsl="http://exslt.org/common"
    xmlns:func="http://exslt.org/functions"

    extension-element-prefixes="exsl func">

<xsl:template match="class" mode="view-files">
    <xsl:param name="moduleNode" select="ancestor::module"/>
    <xsl:param name="subsystem" select="$moduleNode/@subsystem"/>
    <xsl:param name="entityName" select="func:lowercasefirstletter(@name)"/>
    <xsl:param name="viewroot"/>
    
    <exsl:document href="{$viewroot}/{$subsystem}/{$entityName}/index.xhtml"
        method="xml"
        encoding="utf-8"
        indent="yes"
        >
        <xsl:call-template name="index-xhtml"/>
    </exsl:document>
    
    <exsl:document href="{$viewroot}/{$subsystem}/{$entityName}/form.xhtml"
        method="xml"
        encoding="utf-8"
        indent="yes"
        >
        <xsl:call-template name="form-xhtml"/>
    </exsl:document>
</xsl:template>

<xsl:template name="index-xhtml">
    <xsl:param name="entityName" select="func:lowercasefirstletter(@name)"/>
    <xsl:param name="controllerName" select="concat($entityName, 'Controller')"/>

    <ui:composition>
        <ui:define name="title">
            <h:outputText value="#{{msgs['{$entityName}.title']}}" />
        </ui:define>

        <ui:define name="description">
            <h:outputText value="#{{msgs['{$entityName}.title.description']}}" />
        </ui:define>

        <ui:define name="body">
            <adm:breadcrumb link="/core/{$entityName}/index" title="#{{msgs['{$entityName}.title']}}" />
            <h:form id="listingForm">
                <p:dataTable id="entitiesDataTable" var="entity" value="#{{{$controllerName}.lazyEntities}}"
                    selection="#{{{$controllerName}.selectedEntity}}" selectionMode="single" paginator="true"
                    rows="#{{{$controllerName}.maxDataTableRowsPerPage}}"
                    paginatorTemplate="{{RowsPerPageDropdown}} {{FirstPageLink}} {{PreviousPageLink}} {{CurrentPageReport}} {{NextPageLink}} {{LastPageLink}}"
                    rowsPerPageTemplate="#{{{$controllerName}.halfMaxDataTableRowsPerPage}},#{{{$controllerName}.maxDataTableRowsPerPage}},#{{{$controllerName}.doubleMaxDataTableRowsPerPage}}"
                    lazy="true" paginatorPosition="bottom" emptyMessage="#{{msgs['{$entityName}.text.noEntities']}}">
                    <p:ajax event="rowSelect" update="buttonsGroup" />
                    <p:ajax event="rowUnselect" update="buttonsGroup" />
                    <f:facet name="header">
                        <h:outputText value="#{{msgs['{$entityName}.text.entities']}}" />
                    </f:facet>
                    <xsl:for-each select="field">
                        <p:column headerText="#{{msgs['{$entityName}.field.{@name}']}}">
                            <h:outputText value="#{{entity.{@name}}}">
                                <xsl:if test="@type='Date'">
                                    <f:convertDateTime type="date" pattern="#{{msgs['jbutler.format.date.java']}}" />
                                </xsl:if>
                            </h:outputText>
                        </p:column>
                    </xsl:for-each>
                    <f:facet name="footer">
                        <h:panelGroup id="buttonsGroup">
                            <p:commandButton action="#{{{$controllerName}.create}}" icon="fa fa-plus" value="#{{msgs['jbutler.crud.button.create']}}" />
                            <p:commandButton action="#{{{$controllerName}.retrieve}}" icon="fa fa-search" value="#{{msgs['jbutler.crud.button.retrieve']}}"
                                disabled="#{{{$controllerName}.selectedEntity == null}}" />
                            <p:commandButton action="#{{{$controllerName}.update}}" icon="fa fa-edit" value="#{{msgs['jbutler.crud.button.update']}}"
                                disabled="#{{{$controllerName}.selectedEntity == null}}" />
                            <p:commandButton action="#{{{$controllerName}.trash}}" icon="fa fa-trash" value="#{{msgs['jbutler.crud.button.delete']}}"
                                disabled="#{{{$controllerName}.selectedEntity == null}}" update=":listingForm:trashGroup" />
                        </h:panelGroup>
                    </f:facet>
                </p:dataTable>
                <h:panelGroup id="trashGroup">
                    <hr />
                    <p:panel id="trashPanel" header="#{{msgs['jbutler.crud.text.trashHeader']}}" toggleable="true" toggleSpeed="500"
                        rendered="#{{not empty {$controllerName}.trashCan}}">
                        <p:dataTable id="trashDataTable" var="entity" value="#{{{$controllerName}.trashCan}}">
                            <xsl:for-each select="field">
                                <p:column headerText="#{{msgs['{$entityName}.field.{@name}']}}">
                                    <h:outputText value="#{{entity.{@name}}}">
                                        <xsl:if test="@type='Date'">
                                            <f:convertDateTime type="date" pattern="#{{msgs['jbutler.format.date.java']}}" />
                                        </xsl:if>
                                    </h:outputText>
                                </p:column>
                            </xsl:for-each>
                            <f:facet name="footer">
                                <p:commandButton action="#{{{$controllerName}.cancelDeletion}}" value="#{{msgs['jbutler.crud.button.cancelDeletion']}}"
                                    icon="fa fa-fw fa-close" process="@this" update=":listingForm" />
                                <p:commandButton action="#{{{$controllerName}.delete}}" value="#{{msgs['jbutler.crud.button.confirmDeletion']}}"
                                    icon="fa fa-fw fa-trash-o" process="@this" update=":listingForm" />
                            </f:facet>
                        </p:dataTable>
                    </p:panel>
                </h:panelGroup>
            </h:form>
        </ui:define>
    </ui:composition>
</xsl:template>

<xsl:template name="form-xhtml">
    <xsl:param name="entityName" select="func:lowercasefirstletter(@name)"/>
    <xsl:param name="controllerName" select="concat($entityName, 'Controller')"/>

    <ui:composition>
        <ui:define name="title">
            <h:outputText value="#{{msgs['{$entityName}.title.create']}}"
                rendered="#{{(! {$controllerName}.readOnly) and ({$controllerName}.selectedEntity.id == null)}}" />
            <h:outputText value="#{{msgs['{$entityName}.title.update']}}"
                rendered="#{{(! {$controllerName}.readOnly) and ({$controllerName}.selectedEntity.id != null)}}" />
            <h:outputText value="#{{msgs['{$entityName}.title.retrieve']}}" rendered="#{{{$controllerName}.readOnly}}" />
        </ui:define>

        <ui:define name="description">
            <h:outputText value="#{{msgs['{$entityName}.title.create.description']}}"
                rendered="#{{(! {$controllerName}.readOnly) and ({$controllerName}.selectedEntity.id == null)}}" />
            <h:outputText value="#{{msgs['{$entityName}.title.update.description']}}"
                rendered="#{{(! {$controllerName}.readOnly) and ({$controllerName}.selectedEntity.id != null)}}" />
            <h:outputText value="#{{msgs['{$entityName}.title.retrieve.description']}}" rendered="#{{{$controllerName}.readOnly}}" />
        </ui:define>

        <ui:define name="body">
            <h:form id="entitiesForm">
                <p:panelGrid columns="2" columnClasses="ui-grid-col-4,ui-grid-col-8" layout="grid" styleClass="ui-panelgrid-blank">
                    <xsl:for-each select="field">
                        <p:outputLabel for="{@name}Field" value="#{{msgs['{$entityName}.field.{@name}']}}" />
                        <xsl:choose>
                            <xsl:when test="@type='Date'">
                                <p:calendar id="{@name}Field" 
                                    value="#{{{$controllerName}.selectedEntity.{@name}}}" showOn="button"
                                    pattern="dd/MM/yyyy" mask="true" style="width: 100%;" />
                            </xsl:when>
                            <xsl:otherwise>
                                <p:inputText id="{@name}Field" value="#{{{$controllerName}.selectedEntity.{@name}}}" style="width: 100%;" />
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:for-each>
                </p:panelGrid>
                <p:commandButton id="cancelButton" value="#{{msgs['jbutler.crud.button.cancel']}}" icon="fa fa-close"
                    action="#{{{$controllerName}.list}}" immediate="true" rendered="#{{! {$controllerName}.readOnly}}" />
                <p:commandButton id="saveButton" value="#{{msgs['jbutler.crud.button.save']}}" icon="fa fa-save" action="#{{{$controllerName}.save}}"
                    rendered="#{{! {$controllerName}.readOnly}}" />
                <p:defaultCommand target="saveButton" />
                <p:commandButton id="backButton" value="#{{msgs['jbutler.crud.button.back']}}" icon="fa fa-arrow-circle-left"
                    action="#{{{$controllerName}.list}}" immediate="true" rendered="#{{{$controllerName}.readOnly}}" />
            </h:form>
        </ui:define>
    </ui:composition>
</xsl:template>

</xsl:stylesheet>
