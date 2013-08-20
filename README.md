
###斷詞處理函式庫
原本打算要用中研院的[中文斷詞系統](http://ckipsvr.iis.sinica.edu.tw/)，但是看過服務說明之後覺得頗麻煩，
所以找了[mmseg4j](https://code.google.com/p/mmseg4j/)中文分詞器來用。  
mmseg4j所使用的詞庫是[mmseg4j_data](https://github.com/chyiro/moedict-data-chunker/tree/master/mmseg4j_data)裡的
words.dic，這個檔案的內容是用正規表示式從dict-revised.json裡抓出來的。

### 處理對象與輸出格式
處理的對象為各條目的definitions裡的各欄位。其中type因為是詞性只有一個字，所以略過不處理。  
斷詞處理後的各個獨立詞之間用分號連結，輸出至definitions裡名為"chunked_xxx"的欄位(xxx為斷詞處理前的欄位名) 。  
下面是「講義」條目的斷句結果。

    {
        "title": "講義",
        "heteronyms": [
            {
                "definitions": [
                    {
                        "chunked_quote": "南史;卷;七;梁武帝;本紀;下;初;帝;創;同;泰;寺;至;是;開;大通;門;以;對;寺;之;南門;取;反語;以;協同;泰;自是;晨夕;講義;多;由此;門;",
                        "def": "闡釋說明書籍的義理。",
                        "chunked_def": "闡釋;說明;書籍;的;義理;",
                        "quote": [
                            "南史．卷七．梁武帝本紀下：「初，帝創同泰寺，至是開大通門以對寺之南門，取反語以協同泰。自是晨夕講義，多由此門。」"
                        ]
                    },
                    {
                        "chunked_quote": "宋;邢昺;孝經;注疏;序;會合;歸;趣;一;依;講;說;次第;解釋;號;之;為;講義;也;",
                        "def": "古時臣子將講授給帝王研習的資料預先撰擬出來，稱為「講義」。",
                        "chunked_def": "古時;臣子;將;講授;給;帝王;研習;的;資料;預先;撰;擬;出來;稱;為;講義;",
                        "quote": [
                            "宋．邢昺．孝經注疏序：「會合歸趣，一依講說，次第解釋，號之為講義也。」"
                        ]
                    },
                    {
                        "def": "學校教師為學生編輯的講授資料。",
                        "chunked_def": "學校;教師;為學;生;編輯;的;講授;資料;"
                    }
                ],
                "pinyin": "jiǎng yì",
                "bopomofo2": "jiǎng yì",
                "bopomofo": "ㄐㄧㄤˇ ㄧˋ"
            }
        ]
    }
