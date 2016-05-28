<!-- This XSL extract job information from rssquery.xml -->
<!-- Running command: java -jar saxon9he.jar rssquery.xml job2Rss.xsl keyword=Java > out.xml  -->
<!-- by Zenglin Wang -->

<xsl:stylesheet
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
     version="1.1">
<xsl:output indent="yes"/>

<xsl:template match="/">
  <rss version="2.0">
    <channel>
      <title><xsl:value-of select="//channel/title"/></title>
      <description><xsl:value-of select="//channel/description"/></description>
      <link><xsl:value-of select="//channel/link"/></link>
      <image>
        <title><xsl:value-of select="//channel/image/title"/></title>
        <url><xsl:value-of select="//channel/image/url"/></url>
        <link><xsl:value-of select="//channel/image/link"/></link>
        <width><xsl:value-of select="//channel/image/width"/></width>
        <height><xsl:value-of select="//channel/image/height"/></height>
      </image>
      <language><xsl:value-of select="//channel/language"/></language>
      <copyright><xsl:value-of select="//channel/copyright"/></copyright>
      <webMaster><xsl:value-of select="//channel/webMaster"/></webMaster>
      <pubDate><xsl:value-of select="//channel/pubDate"/></pubDate>
      <docs><xsl:value-of select="//channel/docs"/></docs>
      <generator><xsl:value-of select="//channel/generator"/></generator>

      <xsl:for-each select="//item">
        <item>
          <title><xsl:value-of select="title"/></title>
          <description><xsl:value-of select="description"/></description>
          <link><xsl:value-of select="link"/></link>
          <guid><xsl:value-of select="guid"/></guid>
          <pubDate><xsl:value-of select="pubDate"/></pubDate>
        </item>
      </xsl:for-each>

    </channel>
  </rss>
</xsl:template>

</xsl:stylesheet>
