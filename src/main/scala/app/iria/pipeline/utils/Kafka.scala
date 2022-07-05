package app.iria.pipeline.utils

import com.typesafe.config.Config

import scala.collection.JavaConverters._

object Kafka {

    /**
     *
     * Converts a typesafe Config to a Map[String,Object] for getting Kafka properties
     *
     * This method assumes that the object passed in will contain the kafka properties you want to set.
     * If you have a parent object, you would pass the config where the nested values are Kafka properties that you want to set.
     *
     * @param config
     * @return
     */
    def mapConfig( config : Config ) : Map[ String, String ] = {
        config
          .entrySet()
          .asScala
          .map( e => e.getKey -> e.getValue.unwrapped().toString )
          .toMap
    }

}
