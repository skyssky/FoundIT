<!-- This XSL extract job information from rssquery.xml -->
<!-- Running command: java -jar saxon9he.jar rssquery.xml job2.xsl > out.xml -->
<!-- by Zenglin Wang -->

<xsl:stylesheet
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
     version="1.1">
<xsl:output indent="yes"/>

<xsl:template match="/">
  <jobs>
    <xsl:for-each select="//item">
      <job>
        <title><xsl:value-of select="title"/></title>
        <description><xsl:value-of select="description"/></description>
        <link><xsl:value-of select="link"/></link>
        <guid><xsl:value-of select="guid"/></guid>
        <pubDate><xsl:value-of select="pubDate"/></pubDate>
      </job>
    </xsl:for-each>
  </jobs>
</xsl:template>

</xsl:stylesheet>
