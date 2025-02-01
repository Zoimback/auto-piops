package main.jenkins.data

@GrabConfig(systemClassLoader=true)
@Grab('mysql:mysql-connector-java:8.0.26')

import groovy.sql.Sql


/**
 * Clase que contiene métodos para ejecutar queries en una base de datos
 */
class DBconector {


    def pipelineContext

    /**
    * Constructor que recibe el contexto de la pipeline
    * @param pipelineContext El contexto de la pipeline (this)
    */

    DBconector(def pipelineContext) {
        this.pipelineContext = pipelineContext
    }

    private def getConnection() {
        def credentials = pipelineContext.credentials('CredencialesSQL')
        def url = pipelineContext.credentials('UrlSQL')
        def dbDriver = 'com.mysql.cj.jdbc.Driver'
        return Sql.newInstance(url, credentials.username, credentials.password, dbDriver)
    }
    


    def executeQuery(String query) {
        def sql = getConnection()
        try {
            sql.eachRow(query) { row ->
                println row
            }
        } finally {
            sql.close()
        }
    }   
}