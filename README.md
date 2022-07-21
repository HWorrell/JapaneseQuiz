# JapaneseQuiz

This program is intended to help with Japanese language learning.  There are two possible modes it can be run in:

1: Self Quiz - This mode takes in tab delimited text files with the following information on each line:

Kana Reading
Kanji (Optional)
Definition

The easiest way to produce these files is to enter the information in Excel, then save as "Unicode Text" format.

If a Kanji is present, for each question a random choice will be made from the following options:

Show Kanji, ask for reading
Show Kanji, ask for definition
Show reading, ask for Kanji
Show reading, ask for definition
Show definition, ask for Kanji
Show definition, ask for reading

If no Kanji is present, then the options above including Kanji will automatically be excluded.

2: "Other Quiz" - This mode functions as above, however it also contains a Romaji parser to change any readings into their equivelent Romaji with a space separation, so that someone that is not able to read Hiragana and Katakana can quiz you.
