<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:jee="http://java.sun.com/xml/ns/javaee"

    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:exsl="http://exslt.org/common"
    xmlns:func="http://exslt.org/functions"

    exclude-result-prefixes="jee"
    extension-element-prefixes="exsl func">

<!--
     This stylesheet contains templates that implement funcionality
     of editing file faces-config.xml, adding an entry for a resource-bundle,
     if there is no previous resource-bundle with the same var name.

     This stylesheet is imported by message-file stylesheet.
-->

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

<xsl:template match="node()|@*" mode="faces-config">
    <xsl:param name="params"/>
    <xsl:copy>
        <xsl:copy-of select="@*"/>
        <xsl:apply-templates select="node()" mode="faces-config">
            <xsl:with-param name="params" select="$params"/>
        </xsl:apply-templates>
    </xsl:copy>
</xsl:template>

<xsl:template match="jee:resource-bundle" mode="faces-config">
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

</xsl:stylesheet>
