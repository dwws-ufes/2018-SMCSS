<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:jee="http://java.sun.com/xml/ns/javaee"

    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:exsl="http://exslt.org/common"
    xmlns:func="http://exslt.org/functions"

    exclude-result-prefixes="jee"
    extension-element-prefixes="exsl func">

<!--
     This stylesheet create the message files for a module. The message files
     are specified as message-file elements, grouped under a message-files
     element in input.

     Children elements of message-file generate text lines in resulting file.

     Example:
        <message-files>
            <message-file name="messages.properties">
                ...
            </message-file>
            <message-file name="messages_pt_BR.properties">
                ...
            </message-file>
        </message-files>
 -->

<xsl:import href="faces-config.xsl"/>

<xsl:template match="module" mode="message-file">
    <xsl:variable name="packageName" select="concat(@organization, '.', @system, '.', @subsystem)"/>
    <xsl:variable name="folderName" select="translate($packageName, '.', '/')"/>
    <xsl:variable name="context" select="."/>

    <!-- Register resource bundle pointing to messages in faces-config. See faces-config.xsl -->
    <xsl:call-template name="faces-config"/>

    <!-- Generate message files, one for each message-file element in input -->
    <xsl:for-each select="//message-files/message-file">
        <exsl:document href="{$rootFolder}/../resources/{$folderName}/{@name}" method="text">
            <xsl:apply-templates select="exsl:node-set($context)" mode="message-file-content">
                <xsl:with-param name="messageFile" select="."/>
            </xsl:apply-templates>
        </exsl:document>
    </xsl:for-each>
</xsl:template>

<xsl:template match="module|class|field" mode="message-file-content">
    <xsl:param name="messageFile"/>
    <xsl:apply-templates select="exsl:node-set($messageFile)/for-each[@element=name(current())]" mode="message-file-content">
        <xsl:with-param name="context" select="."/>
    </xsl:apply-templates>
    <xsl:apply-templates mode="message-file-content">
        <xsl:with-param name="messageFile" select="$messageFile"/>
    </xsl:apply-templates>
</xsl:template>

<xsl:template match="text-line" mode="message-file-content">
    <xsl:param name="context"/>
    <xsl:apply-templates mode="message-file-content">
        <xsl:with-param name="context" select="$context"/>
    </xsl:apply-templates>
    <xsl:value-of select="$n"/>
</xsl:template>

<xsl:template match="class-name" mode="message-file-content">
    <xsl:param name="context"/>
    <xsl:value-of select="exsl:node-set($context)/ancestor-or-self::class/@name"/>
</xsl:template>

<xsl:template match="entity-name" mode="message-file-content">
    <xsl:param name="context"/>
    <xsl:value-of select="func:lowercasefirstletter(exsl:node-set($context)/ancestor-or-self::class/@name)"/>
</xsl:template>

<xsl:template match="field-name" mode="message-file-content">
    <xsl:param name="context"/>
    <xsl:value-of select="exsl:node-set($context)/ancestor-or-self::field/@name"/>
</xsl:template>

<xsl:template match="text-line//text()" mode="message-file-content">
    <xsl:value-of select="."/>
</xsl:template>

<xsl:template match="text()" mode="message-file-content"/>

</xsl:stylesheet>
