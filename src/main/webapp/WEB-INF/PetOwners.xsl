<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:wsd="https://au.edu.uts.wsd/pet-manager" exclude-result-prefixes="wsd"
    version="2.0">
    <xsl:output method="html" indent="yes"/>

    <xsl:template match="/">
        <div class="album py-5 bg-light">
            <div class="container">
                <xsl:apply-templates/>
            </div>
        </div>
        
        <section class="jumbotron text-center bg-light">
            <div class="container">
                <h1 class="jumbotron-heading">
                    The data above was rendered using JSP and XSLT.
                </h1>
            </div>
        </section>
    </xsl:template>
    
    <xsl:template match="wsd:person">
        <div class="row">
            
            <xsl:variable name="total">
                <xsl:value-of select="count(wsd:pets/wsd:animal)"/>
            </xsl:variable>
            
            <div class="col-md-4" id="person-{@id}">
                <div class="card mb-4 box-shadow">
                    <div class="card-header text-center">
                        User Profile
                    </div>
                    <div class="card-body">
                        <p class="card-text">
                            <strong>
                                <xsl:value-of select="wsd:name"/>
                            </strong>
                        </p>
                        <p class="text-muted">
                            Contact: <a class="card-link" href="mailto:{wsd:email}">
                                <xsl:value-of select="wsd:email"/>
                            </a>
                        </p>
                        
                        <div class="d-flex justify-content-between align-items-center">
                            <small class="text-muted">Dogs: <xsl:value-of select="count(wsd:pets/wsd:animal[wsd:species = 'DOG'])"/></small>
                            <small class="text-muted">Cats: <xsl:value-of select="count(wsd:pets/wsd:animal[wsd:species = 'CAT'])"/></small>
                            <small class="text-muted">Birds: <xsl:value-of select="count(wsd:pets/wsd:animal[wsd:species = 'BIRD'])"/></small>
                            <small class="text-muted">Total: <xsl:value-of select="$total"/></small>
                        </div>
                    </div>
                    <xsl:if test="$total &gt; 0">
                        <div class="card-footer">
                            <button class="btn btn-sm btn-outline-secondary" type="button" data-toggle="collapse" data-target="#pets-{@id}" aria-controls="pets-{@id}" aria-expanded="false" aria-label="Display {wsd:name}'s pets.">
                                Toggle
                            </button>
                        </div>
                    </xsl:if>
                </div> 
            </div>
        </div>
        
        <div class="row collapse" id="pets-{@id}">
            <xsl:apply-templates select="wsd:pets"/>
        </div>
        
    </xsl:template>
    
    <xsl:template match="wsd:animal">
        <div class="col-md-4">
            <div class="card mb-4 box-shadow">
                <img class="card-img-top" src="{wsd:imageURL}" width="192" height="192" alt="A picture of {//wsd:name}'s {wsd:species} {wsd:name}."/>
                <div class="card-body">
                    <p class="card-text">
                        <strong>
                            <xsl:value-of select="wsd:name"/>
                        </strong>
                        <span class="text-muted">, <xsl:value-of select="wsd:species"/></span>
                    </p>
                    <small class="text-muted">
                        Microchip: <xsl:value-of select="@microchip"/>
                    </small>
                        
                    <div class="d-flex justify-content-between align-items-center">
                        <a href="#person-{//@id}">Owners Profile</a>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>
