
@Grapes([
    @Grab(group='com.chenlb.mmseg4j', module='mmseg4j-core', version='1.9.1'),
    @Grab(group='com.chenlb.mmseg4j', module='mmseg4j-analysis', version='1.9.1'),
    @Grab(group='com.chenlb.mmseg4j', module='mmseg4j-solr', version='1.9.1')
])

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.json.StringEscapeUtils

import com.chenlb.mmseg4j.ComplexSeg
import com.chenlb.mmseg4j.Dictionary
import com.chenlb.mmseg4j.MMSeg
import com.chenlb.mmseg4j.Seg
import com.chenlb.mmseg4j.Word


/* input and output file */
//File source = new File("moedict_data", "sample.json") // sample
//File target = new File("moedict_data", "chunked_sample.json") // sample
File source = new File("moedict_data", "dict-revised.json")
File target = new File("moedict_data", "dict-revised-chunked.json")

/* initialize mmseg4j */
System.setProperty("mmseg.dic.path", "mmseg4j_data");
Dictionary dic = Dictionary.getInstance()
Seg seg = new ComplexSeg(dic)

/* separator for chunked strings */
String SEP = ";"

/* closure that does chunking */
def chunk = { String str ->
    /* do chunking */
    MMSeg mmSeg = new MMSeg(new StringReader(str), seg)

    /* convert chunked words to single string */
    def chunkedTokens = ""
    Word word = mmSeg.next()
    while (word) {
        chunkedTokens += (word.getString() + SEP)
        word = mmSeg.next()
    }
    chunkedTokens
}

/* for each article in original file,
 * chunk values for each key except for "type" and add result to container */
def chunkedJsons = []
new JsonSlurper().parse(source.newReader()).each { article ->
    def title = article.title
    article.heteronyms.definitions.each { entry ->
        entry.each { definition ->
            def toBeChunked = definition.findAll{ it.key != "type" }
            toBeChunked.each { key, value ->
                definition.put("chunked_" + key, chunk((String)value))
            }
        }
    }
    chunkedJsons << article
}

/* get string representation of chunked result */
def builder = new JsonBuilder()
builder.call(chunkedJsons)
def str = StringEscapeUtils.unescapeJava(builder.toPrettyString())

/* write to file */
target.write(str)

