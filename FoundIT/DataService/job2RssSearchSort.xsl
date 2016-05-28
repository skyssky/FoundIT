<!-- This file is used to extract job alerts by keyword -->
<!-- Running command: java -jar saxon9he.jar rssquery.xml job2RssSearchSort.xsl keyword=Java > out.xml  -->


<xsl:stylesheet
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
     version="1.0">
<xsl:output indent="yes"/>   

<xsl:param name="keyword"/>

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

  		<!-- <xsl:apply-templates select="jobAlert[contains(position,$keyword) or contains(detail,$keyword)]"> -->
      <xsl:apply-templates select="//item[contains(lower-case(.),lower-case($keyword))]">
        <xsl:sort
           data-type="text"
           order="ascending" 
           select="title"/>
  		</xsl:apply-templates>
	
    </channel>
  </rss>
</xsl:template>

<xsl:template match="item">
  <item>
    <title><xsl:value-of select="title"/></title>
    <description><xsl:value-of select="description"/></description>
    <link><xsl:value-of select="link"/></link>
    <guid><xsl:value-of select="guid"/></guid>
    <pubDate><xsl:value-of select="pubDate"/></pubDate>
  </item>
</xsl:template>


</xsl:stylesheet>



