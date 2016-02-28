__author__ = 'sharma.varun'
dictionary = ["rag","on","ball","all","club","dragon","ballc","lub"]
# dictionary = ["a","aa","aaa"]
word = "dragonballclub"
# word = "aaaa"
# break this word into meaningfull sentence based on dictionary of words
def meaningfull_sentence(word, result):
    if len(word)==0:
        print result
    for i in range(1,len(word)+1):
        if word[:i] in dictionary:
            meaningfull_sentence(word[i:],result+[word[:i]])

meaningfull_sentence(word,[])