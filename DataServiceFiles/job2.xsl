<!-- This XSL extract job information from rssquery.xml -->
<!-- Running command: java -jar saxon9he.jar rssquery.xml job2.xsl > out.xml -->
<!-- by Zenglin Wang -->

<xsl:stylesheet
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
     version="1.1">
<xsl:output indent="yes"/>

<xsl:template match="/">
  <jobAlerts>
    <xsl:for-each select="//item">
      <jobAlert>
        <position><xsl:value-of select="title"/></position>
        <detail><xsl:value-of select="description"/></detail>
        <!-- <link><xsl:value-of select="link"/></link> -->
        <!-- <guid><xsl:value-of select="guid"/></guid> -->
        <!-- <pubDate><xsl:value-of select="pubDate"/></pubDate> -->
      </jobAlert>
    </xsl:for-each>
  </jobAlerts>
</xsl:template>

</xsl:stylesheet>
