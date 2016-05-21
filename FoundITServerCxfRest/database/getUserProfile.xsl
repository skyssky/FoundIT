<xsl:stylesheet
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
     version="1.0">
<xsl:output indent="yes"/>   

<xsl:param name="username"/>

<xsl:template match="UserProfiles">
  <xsl:apply-templates select="UserProfile[username=$username]">
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="UserProfile">
  <username><xsl:value-of select="username"/></username>
  <profileId><xsl:value-of select="profileId"/></profileId>
  <detail><xsl:value-of select="detail"/></detail>
  <skill><xsl:value-of select="skill"/></skill>
  <experience><xsl:value-of select="experience"/></experience>
</xsl:template>

</xsl:stylesheet>


