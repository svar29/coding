__author__ = 'sharma.varun'
dictionary = ["rag", "on", "ball", "all", "club", "dragon", "ballc", "lub"]
# com.uber.dictionary = ["a","aa","aaa"]
word = "dragonballclub"


# word = "aaaa"
# break this word into meaningfull sentence based on com.uber.dictionary of words
def meaning_full_sentence(word, result):
    if len(word) == 0:
        print result
    for i in range(1, len(word) + 1):
        if word[:i] in dictionary:
            print word[i:]
            meaning_full_sentence(word[i:], result + [word[:i]])


meaning_full_sentence(word, [])


def meaning_full_sentence_dp(word, result, dp):
    if len(word) == 0:
        print result
        return True
    for i in range(1, len(word) + 1):
        if word[:i] in dictionary and dp.get(word[i:], True):
            print word[i:]
            if meaning_full_sentence_dp(word[i:], result + [word[:i]], dp):
                dp[word[i:]] = True
            else:
                dp[word[i:]] = False
    return False


dp = {}
meaning_full_sentence_dp(word, [], dp)
