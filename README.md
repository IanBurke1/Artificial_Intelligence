# Using Simulated Annealing to Break a Playfair Cipher
#### *Artificial Intelligence - Lecturer: [Dr John Healy](https://github.com/bradanfeasa) - 4th Year (Hons) Software Development, GMIT*
For my project in Artifical Intelligence, I am required to use the simulated annealing algorithm to break a Playfair Cipher. The application should have the following minimal set of features:

- A menu-drivencommand line UI that enables a cipher-text source to be specified (a file or URL) and an output destination file for decrypted plain-text.
- Decrypt cipher-text with asimulated annealing algorithm that uses a log-probability and n-gram statistics as a heuristic evaluation function. 

## How to run
#### Run on eclipse
1. Download the [zip.](https://github.com/ianburkeixiv/Artificial_Intelligence/archive/master.zip)
2. Unzip the repository.
3. Import Simulated Annealing project folder into eclipse and run.

#### Run using command terminal
1. Download the [zip.](https://github.com/ianburkeixiv/Artificial_Intelligence/archive/master.zip)
2. Unzip the repository.
3. Open command terminal.
4. Enter the following:
```bash
java -cp ./playfair.jar ie.gmit.sw.ai.CipherBreaker
```
## Architecture
When the application runs, a console UI menu is displayed that gives four options to the user. Option one allows a user to decrypt a cipher using SA by entering their cipher text into the console. Option two allows the user to decrypt a cipher using SA by entering a file that contains the cipher-text. Option three allows the user to encrypt their message by entering a keyword for the playfair 5x5 table and their plaintext message. Option four allows the user to exit the program. When SA process is completed, the winning key and decrypted text is written into a text file and saved in the current directory.

## Playfair Cipher
Playfair is a digraphic substitution cipher based on a 5 x 5 square. A key is written into the square, either based on a keyword or randomly. For example, in the block below the keyword ‘abstruse’ has been written in horizontally, the 'I' standing also for 'J':

#### A B S T R
#### U E C D F
#### G H I K L
#### M N O P Q
#### V W X Y Z

A plaintext is enciphered two letters at a time. If a digram contains two letters that are the same an 'x' is inserted between them. If a message has an odd number of letters, a null is added at the end, usually 'x' or 'q'. There are three enciphering rules:

1. When both letters of the digram appear in the same row, the letters that follow in that row are taken as the cipher letters: thus ‘bt’ enciphers to ‘SR’. If the plain letter is at the end of a row, then the letter that follows is the letter at the start of the same row; so ‘tr’ enciphers as ‘RA’.

2. plain letters in the same column are enciphered with letters that follow in that column. So ‘bh’ is enciphered ‘EN’.

3. plain letters in different rows and columns are enciphered with letters at the free ends of the rectangle that is formed by the two letters. So ‘cp’ enciphers as ‘DO’ and ‘pc’ as ‘OD’.

## Simulated Annealing
The algorithm starts with a random 25 letter key, called the parent key. The parent key is then shuffled/changed in a few ways, for example by swapping two letters, to create a child key. Plaintext is made from parent and from child, and if plaintext from the child is better than from the parent, the child key replaces the parent. Quality of plaintext is judged by scoring each tetragraph/n-gram according to its frequency in the English language i.e. If the text looks similar to English, then the chances are that the key is a good one. The similarity of a given piece of text to English can be computed by breaking the text  into  fixed-length  substrings, called n-grams, and then comparing each substring to an existing map of n-grams and their frequency. This process does not guarantee that the outputted answer will be the correct plain-text, but can give a good approximation that may well be the right answer.

## Hillclimbing vs. Simulated Annealing
Hillclimbing is like trying to climb to the top of a mountain by always following a path that leads upwards. The weakness of this strategy is that such a path may lead to an inferior peak or 'local maximum', where the climber will be literally stuck. It is then necessary to descend and start all over again.

Simulated Annealing is similar to Hillclimbing, but allows an occasional descent in an effort to avoid the 'local maximum'. This small modification makes a huge improvement, enabling Simulated Annealing to solve where Hillclimbing fails. The descent is made by occasionally accepting a child key that scores lower than the parent key. The probability of acceptance is related to how much worse the score is between child and parent.

Simulated Annealing is more successful when a given cipher is lengthy. It is not so successful when a cipher is short.

## Optimum Temperature
To find the optimum temperature, we get the ciphertext.length() and apply it to the following equation:

Optimum Temperature = 10 + 0.087*(ciphertext.length()-84)

In this project, I changed the 10 to 12 which seemed to speed up the SA process.

## Using n-Gram Statistics as a Heuristic Function
An n-gram or tetragraph is a substring of a word(s) of length n (usually 4) and can be used to measure how simliar some decrypted text is to English. A fitness  measure  or  heuristic  score  can  be  computed  from  the  frequency  of occurrence of a 4-gram, q, as follows: P(q) = count(q) / n, where n is the total number of 4-grams from a large sample source. To get an overall probability of words/phrase by getting the sum of log probabilities. E.g. log10(HAPPYDAYS) = log10(P(HAPP))+log10(P(APPY))+log10(P(PPYD))+log10(P(PYDA))+log10(P(YDAY))

The 4grams.txt is a  text  file  containing  a  total  of 389,3734-grams,  from  a maximum  possible  number  of  264=456,976. This file is parsed in from the Quadgrams class and used in SA to determine the fitness of the decrypted text.

