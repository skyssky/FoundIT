<!-- This file is used to extract job alerts by keyword -->
<!-- Running command: java -jar saxon9he.jar joblist2.xml job2Search.xsl keyword=Senior > job2SearchResult.xml -->


<xsl:stylesheet
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
     version="1.0">
<xsl:output indent="yes"/>   

<xsl:param name="keyword"/>

<xsl:template match="jobAlerts">
	<jobAlerts>
<!--   		<xsl:apply-templates select="jobAlert[contains(lower-case(position),$keyword) or contains(lower-case(detail),lower-case($keyword))]"> -->
      <xsl:apply-templates select="jobAlert[contains(lower-case(.),lower-case($keyword))]">
  		</xsl:apply-templates>
	</jobAlerts>
</xsl:template>

<xsl:template match="jobAlert">
	<jobAlert>
  		<position><xsl:value-of select="position"/></position>
  		<detail><xsl:value-of select="detail"/></detail>
      <guid><xsl:value-of select="guid"/></guid>
      <link><xsl:value-of select="link"/></link>
      <pubDate><xsl:value-of select="pubDate"/></pubDate>
  	</jobAlert>
</xsl:template>


</xsl:stylesheet>



