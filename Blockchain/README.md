<html> <head> <title> Distributed Systems&mdash;Elliott  </center> </title>
</head>
<body>
<FONT FACE="Cambria" SIZE=1>

4.4 <br> <br> 

Updates: 
<ol>
<li> 2020-05-10: Added Blockchain for Beginneres link. Thanks Said!
<li> 2020-05-06. Further clarification of multicast=send.
</ol>

<FONT FACE="Cambria" SIZE=4>
<center> <h1>Program Three <br> 
Blockchain </h1> 

<FONT FACE="Cambria" SIZE=3>
<h2> Elliott&mdash;Distributed Systems<br> </h2>
<h3> Copyright 2020 Clark Elliott All rights reserved </h3>

</center>

<a href=checklist-block.html> Blockchain Checklist </a> <br><br>
<a href=BlockDevStrategy.html> Blockchain Assignment Development
  Strategy </a> <-- Read this!<br><br>

<a href=gson-2.8.2.jar> Gson JSON library jar file </a>
from <a href="https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.2/">
  here</a>.<br><br>

<a href=runblockj.bat> Sample Windows .bat file </a> to compile and run with JSON.<br><br>

<a href=BlockJ.java> Blockchain utilities sample program Version J </a> and sample
<a href=BlockOutputJ.txt> output.</a> using JSON. <br><br>
<a href=BlockInputG.java> Blockchain input utilty program, </a> a 
<a href=BlockInputG.bat> Windows .bat file </a> and
<a href=BlockInputG-output.txt> Sample output </a> using JSON. (With
priority queue TimeStamp comparator)<br>


<a href=BlockInput0.txt> BlockInput0.txt </a> and 
<a href=BlockInput1.txt> BlockInput1.txt </a> and 
<a href=BlockInput2.txt> BlockInput2.txt </a> <br><br>

<a href=ProcessZero.bat> Process Zero script </a> and 
<a href=ProcessOne.bat> Process One Script </a> and 
<a href=ProcessTwo.bat> Process Two Script </a> and 
<a href=BlockMaster.bat> Master Script runs everything </a> <p>

<a href=UnixScriptA.html> Unix script ideas </a><p>

<a href=WorkB.java> Sample Work Program </a> and sample <a href=WorkBOutput.txt> output</a>.<p>


Blockchain <a href=bc.java> Process Coordination </a> sample program and <a
href=bc-output.txt>output</a>.<p>

<a href=http://tutorials.jenkov.com/java-util-concurrent/blockingqueue.html>
Blocking Queue </a> and 
<a
href=http://tutorials.jenkov.com/java-util-concurrent/priorityblockingqueue.html>
Priority Blocking Queue </a> thread-safe code concurrency discussion.<p>

<a href=https://www.fer.unizg.hr/_download/repository/blockchain_ehr.pdf>
Paper on blockchain for
medical records </a> <br>

<a href=https://www.youtube.com/watch?v=Lx9zgZCMqXE> BitCoin YouTube
Video </a><br>



<h2> Legacy XML links for previous students: </h2>
<a href=https://codebeautify.org/xmlviewer> XML tools </a> and 
<a href= https://www.w3schools.com/xml/xml_validator.asp> XML
  Validator </a><br><br>
<a href=Java9Fix.html> Java 9 Fix, and later versions. </a><p>

<h2> Some web references </h2>
<ul>

<p> <li>
    <a href=https://cointelegraph.com/bitcoin-for-beginners/how-blockchain-technology-works-guide-for-beginners>
    How Blockchain works </a> for beginners. Thanks Said!
<li> <a href=https://hyperledger-fabric.readthedocs.io/en/latest/>
HyperLedger</a>


<!-- <li> <a href=https://public.dhe.ibm.com/common/ssi/ecm/xi/en/xim12354usen/ibm-blockchain_second-edition_final_XIM12354USEN.pdf>
IBM Blockchain for Dummies</a>  -->

<li> <a href=https://www.ibm.com/blockchain/what-is-blockchain>
IBM Blockchain for Dummies</a>  Thanks Gries!

<li> <a
href=https://codeburst.io/build-your-first-ethereum-smart-contract-with-solidity-tutorial-94171d6b1c4b>
Tutorial on using ethereum for smart contracts </a> Use private browsing?
Will ask you to register on second visit.
</ul>

<h2> Grading procedures </h2>

File names: Blockchain.java, BlockchainLedger.json, BlockchainLedgerSample.json, BlockInput0.txt,
BlockInput1.txt, BlockInput2.txt, BlockchainLog.txt.

In grading your Blockchain programs we will (at least)...

<ol>
<p><li> check for plagiarism using TII and an array of other plagiarism
checkers.
<p><li> run scripts to automate the following:<p>
<p><li> download and unzip your program and the given data files into a
    directory
<p><li> execute: javac -cp "gson-2.8.2.jar" *.java twice.
<p><li> read your checklist-block.html file
<p><li> erase BlockchainLedger.json [if you happen to have submitted it]
<p><li> execute some version of the master script to run three processes
from your Blockchain.class file using your data input files for each process
with the given names. [start java Blockchain 0 / start java Blockchain 1 /
start java Blockchain 2] NOTE: We will start your processes in the order, 0,
1,2 so you can use process 2 to trigger events based on the assumption that
all three processes are now running.
<p><li> follow your console output in each of the three processes.
<p><li> examine your newly created BlockchainLedger.json file
<p><li> read your BlockchainLedgerSample.json file if there is a problem with
the BlockchainLedger.json file. Be sure to submit it!
<p><li> repeat with our own data files.
<p><li> read your BlockchainLog.txt file that has collected informative
console output from your three running processes. (Copy your console output
into a file manually.)
<p><li> try the console commands you have provided for listing,
verification, reading data files and etc.
<p><li> attend to any special features or bragging rights.
<p><li> read your code in some detail and read your <strong> comments in the
code </strong> that show you understand how this all works.

</ol>

<h2> Learning outcomes </h2>
At the end of this assignment you will:

<ol>
<p><li> Understand the basic structure of blockchain technology
<p><li> Become familiar with some basic public key cryptographic techniques
<p><li> Have a simple running framework for a peer-to-peer shared ledger
system based on a blockchain
<p><li> Understand the basic idea of "work" which is used to guarantee the
validity of cryptocurrencies like Bitcoin
<p><li> Have a collection of code utilities for working with
blockchain technology, and cryptography that can be applied to many
    applications
<p><li> Have a rudimentary working konwledge of JSON

</ol>
<hr>
In this assignment we will learn about the design of a simple
blockchain system to support a ledger shared among networked peers.<p>

Some of the basic crytographic and other techniques you will need are in the
running utilities program (linked above). You are free to use the code in
your assignment, but you MUST REMOVE MY COMMENTS and ADD YOUR OWN COMMENTS
showing that you understand how the code works. Leave in citations of the
original web source locations. Note that this pedagogical code is for
presenting the general ideas of the blockchain design but makes no attempt
to be up to date with the latest details regarding security. Therefor this
code MUST be updated with the latest practices for any actual secure
computing application. <p>

You will probably have to listen to the lectures on blockchain
technology to complete this assignment. You may find the YouTube
video on Bitcoin (linked above) helpful as well, and the links provided to
contemporary blockchain development.<p>

<h3> Glossary: </h3>

<ul> 

<p><li> Blockchain&mdash;a series of data blocks, where the validity of each succeeding block is
dependent upon the previous block (and thus on all previous blocks).

<p><li> Multicast&mdash;messages are sent to all participating multicast
group nodes on a network, but not intended for other nodes in the
network. For this assignment, multicast=send. That is we use the term <i>
      multicast </i> to imply these messages would only be going to
    processes in the blockchain consortium, but for our purposes, with only
    three processes total, it is the sames as broadcast.

<p><li> Work&mdash;A computationally expensive puzzle to solve making it hard to
verify a block. This makes it difficult for one node to create a counterfeit
series of blocks since it is competing against the cooperation of all other
nodes working together to build a valid blockchain. We will use a
    combination of simple <i> real work </i> and <i> fake
</i> work here (the latter simulated with sleep() statements).

<p><li> Proof-of-Work&mdash;A 256-bit hash value that meets the requirements
    to solve the work puzzle. Proof-of-Work results from hashing the data
    (which can include many things such as the Proof-of-Work value from the
    previous block) concatenated with a random value (our guess).

<p><li> SHA-256 hash&mdash;A 256-bit string representing a typically
much-larger string of data. It is all-but impossible to work backwards to reproduce any
sutiable original data from the hash. One string of data deterministically produces the
same SHA-256 hash each time. If even one bit of the data is changed,
it will (with extremely high probability) produce a dramatically
different hash.

<p><li> Public key cryptograhy&mdash;A cryptographic technique using
<i> pairs </i> of keys. When key one is used to encrypt a string of
data bits, the other key must be used to decrypt it. The keys are
publically bound to a stakeholder. One key is kept secret by the
stakeholder and the other is distributed as public. To send a secret
message, the recipient's public key is used; he then decrypts the
message with his private key. To sign a document, the signer uses her
secret key on the document to produce a signature; authorship is
verified when the signer's public key successfully decrypts the
signature.

<p><li> BlockchainLedger.json the output file from your running system
(created by process 0). If you aren't able to create one large compliant JSON
file, then at least concatenate together chunks of compliant JSON.

</ul>

<hr>
<h2> The assignment </h2>

<h3> Submisson: </h3> 
<strong> 
checklist-block.html<br>
Blockchain.java<br>
BlockchainLog.txt<br>
BlockchainLedgerSample.json<br>
[We will generate BlockchainLedger.json from running your program]<br>
BlockInput0.txt, BlockInput1.txt, BlockInput2.txt
</strong><p>

Turn in (1) a single java file, Blockchain.java that will support three
processes 0, 1 and 2 depending on the first arugment passed. (2) Turn in a
single concatenated console logs file, BlockchainLog.txt showing record
input, verification steps, receipt of public keys, listings, network
communications from peers, and verification (and bragging rights
functionality if you have some): Run your programs and display steps on each
console to show us everything you've got. Clearly label the three sections
PROCESS ZERO, PROCESS ONE and PROCESS TWO. (3) Submit your external text
file containing one compliant json ledger containing your individual json blocks
representating the final ledger in the file (BlockchainLedgerSample.json)
populated with blockchain records. That is, run your program on the data
input files and then rename your BlockchainLedger.json file to
BlockchainLedgerSample.json (3) Submit all of your files except your data-input
files, and your checklist concatenated together as .docx or .html or .txt file to the
plagiarism checker as usual.
<p>

Your BlockchainLog.txt file must be generated by <i> your </i> running
program. You can clean it up for readability, and possibly add annotations,
but you are never allowed to add "fake" output from your program.<p>

<b> Simplest overview, in each process: </b>
<ol>
<li> Read in the data from the input files, put into unverified blocks (UBs).
<li> Multicast (send) the UBs to all the processes (in JSON format).
<li> Do the work of verifying a UB. Add it to the blockchain (BC), multicast
the new BC to all the processes (in JSON format).
<li> Repeat until done. Write the BC to disk (in JSON format).
</ol>

<h3> General </h3>


<ul>

<p><li> After working with the mini-projects as you wish, complete the basic
    assignment given here.

<p><li> Read&mdash;and make sure you understand&mdash;the more advanced parts of the
    assignment (for which an implementation is not required).

<p><li> Consider implementing some of the advanced parts of the assignment
    for fun and fame. Make sure you make your work known through the checklist.

<p><li> Start with a dummy first block zero, with a known "Previous Proof of
    Work" string. Solve the puzzle in the usual way to produce a
    Proof-of-Work hash for this first dummy block.

<p><li> Read input from AT LEAST the three provided data files, one file per
process. We will run your program on the provided data files, and then also
on our own data files that have the same format, but not necessarily the
same number of records.

<p><li> Each process maintains a copy of the <i> shared ledger </i> implemented as a
blockchain. Each time the blockchain is updated it is multicast to all other
peers. (Or, you can multicast only the update (new verified block) and add the
    verified block to each local existing blockchain. Each
solution will have its own challenges to address.) <strong> Process
      zero </strong> is
responsible for writing the updated BlockchainLedger.json file to disk.  We
will use only three processes, but your system design should&mdash;in
theory&mdash;work for any number of peers with minor modifications.

<p><li> Each process reads in a data file to create new records.  A new
record is placed in an unverified block. The block is marshaled as JSON and multicast to all the
processes in the blockchain group which begin competing with one another to
solve the "work" puzzle. One process solves a puzzle to verify the block,
prepends the verified block to the blockchain, and multicasts the new
blockchain to all other processes in the group. All other processes abandon
the attempt to verify that block.

<p><li> Every record in the shared ledger is considered canonical. It
is ~impossible to insert a counterfeit block (record) into the blockchain.

<p><li> We will use simple medical records in files, but the data is not really
of concern to us. We could use the same system for a complete audit
trail of business transactions, to support a digital currency, or to
keep track of banking records.

<p><li> Ordinarily many records would comprise a single block; for
simplicity we will use a single record for each block.

<p><li> Your system must run from some version of the provided run
scripts. Adapt as needed for other operating systems. Use the trick that
when <strong> process 2 </strong> starts, it will send a message to start the action on the
other procsses as well.

<p><li> If you have to, you can use sleep() statements arbitrarily inserted
to help with process coordination as needed, but don't sleep too long so
that we can get your program graded! Be sure to add comments for these
coordination statements so we know you understand what is going on.

</ul>

<h3> Ports and servers </h3>

Because we will have multiple participating processes running on the
same machine we will need flexibility to avoid port conflicts. For each process:

<ul>
<p><li> Port 4710+process number receives public keys (4710, 4711, 4712)
<p><li> Port 4820+process number receives unverified blocks (4820, 4821, 4822)
<p><li> Port 4930+process number receives updated blockchains (4930, 4931,
4932)
<p><li> Other ports at your discretion, but please use the same scheme:
    base+process number.
<p><li> Feel free to use ten or twenty ports / servers if for some reason
    you need them, or not. This is entirely up to you.

</ul>

<h3> Initialization </h3>

<ul> 

<p><li> Using the start script, start your servers in the order P0, P1, P2
<p><li> When P2 starts, it also triggers the multicast of public keys, and
starts the whole system running.
<p><li> All processes start with the same initial one-block (dummy entry)
form of the blockchain.
<p><li> After all public keys have been established read the data file for
this process.
<p><li> Create unverified blocks from the data and using JSON as the external data format,
multicast each unverified block in turn to all other processes.
</ul>


<h3> Creating a new record and multicasting the unverified block </h3>

<ul> 

<p><li> Any process can accept local input (in our case from a file) which is then put in an
unverified block. The block is named&mdash;and updated with&mdash;a universally unique ID, a
version of that UUID signed by the creating process, the ID of the
creating process, and the current timestamp [and optionally an SHA-256 hash of
the input data is placed in the DataHash field for auditing
purposes&mdash;see below under DataHash. Not required!].

<p><li> The completed unverified block is marshaled as JSON and multicast to all processes
at the correct unverified block port, including to the creating
process itself. In each process that receives them, unmarshal each block back
into a java object, and then place each block into
a concurrent priority queue, sorted by the timestamp for when the block was created.


<p><li> Each process then, one by one, pops the unverified blocks from the
priority queue, attempts to solve the puzzle and verify the block in
competition with the other processes.<p>

</ul>

<h3> Creating the "work" puzzle </h3>

There are many ways to create "work" for a process. We give the process a
puzzle to solve. It takes some random amount of time within
boundaries. Processes compete to see who can solve the puzzle first.<p>

One way to make work is to create a small random seed string that is concatenated
to some string of data from the block giving us the new longer string we'll call
CAT. Create a 256-bit hash of CAT (using a known algorithm). Examine only the leftmost 16 bits of the
hash value, interpreting them as an unsigned 16-bit number (giving us a
range of 0-65535). If the number is
less than 5000 (or whatever number suits your code) then you have solved the puzzle.<p>

Otherwise, pick a new small random seed string, and repeat the above. Do this
until you have solved the puzzle.<p>

If you change the 5000 target to 2500, the puzzle gets twice as hard, and
takes twice as long. If you require that the answer to the puzzle be
recorded along with the original data, then it is impossible to cheat. <p>

For our purposes we will use real work (see the sample code provided), but
we will make the puzzle easier and use the sleep() method to artificially
extend the work time to a second or two. (This makes debugging and grading
easier.) But because we are looking at the value of the hash, we are still
doing real work that cannot be faked.<p>

For ease of development and grading, don't make the work take more than a a
few seconds on average. In a normal implementation we might want it to take
10 minutes. This can always easily be adjusted dynamically (by making the
puzzle easier or harder) so that no matter how fast the computers get, or
how many cooperating processes we have, the puzzle always takes about the
same amount of time to solve.<p>

<h3> Verifying a block and multicasting the new blockchain </h3>

<ul>

<p><li> [Receive unverified blocks into your unverified-block priority queue.]

<p><li> Select the highest-priority block (lowest timestamp) block from your
priority queue of unverified blocks.

<p><li> Check that the bockID for this block is not already part of the current blockchain. If it
is, discard the unverified block.

<p><li> Verify the signed blockID using the public key of the creating
process. (Or pretend to do so if you have not been successful in restoring
the public key at each node.)

<p><li> Verify the creator-process-signed SHA-256 hash of the data.

<p><li> Note: Depending on how much "work" is required, then while
performing the following work, periodically check to see if the block has
already been verified. If so, abandon this process's attempt to verify
it. That is, after each few attempts at the work, look to see if the
blockchain has been updated, and if so whether this block has been included
in the new version.

<p><li> If the blockID does not appear in the current blockchain we proceed,
and must gather three data items (a,b,c) concatenated together as input to our
puzzle solving <strong> work </strong> exercise.

<p><li> Modify the data in the unverified block as follows: (1) insert a
sequential blockNum that is one greater than the most recent block in the
current blockchain. (2) Insert the verifying process's ID into the unverified
block. (3) Concatenate <b> (a) </b> the SHA-256 Proof-of-Work hash from that previous block
in the blockchain to <b> (b) </b> the updated blockdata of this unverified block, producing the
string "UB". (That is, we include a unique piece of the previous block
inside the data that we are hashing for this block.)

<p><li> Complete the <i> work </i> required to produce a new SHA-256 hash in
the appropriate range after hashing the string UB as follows:
    
<ol> 

<p><li> Repeatedly produce <b> (c) </b> a random guess at the value of a string (the
seed) and further update UB by inserting the seed into the BlockData of UB. This
random guess is your attempt to solve the puzzle. (In the sample, we just
concatenate the seed onto the existing data, but you'll want to insert it
into a slot in the block.)

<p><li> Produce an SHA-256 hash of the seed-updated UB. If it is in the right
range (e.g., when the leftmost 16 bits are interpreted as an unsigned
integer, is the number lower than the threshold? [The lower the threshold,
the more work it takes to guess an appropriate seed.]) then you have solved
the puzzle and completed the necessary <i> work </i> to verify the block. 

<p><li> Repeat until the puzzle is solved.

</ol>

<p><li> Insert the the new Proof-of-Work SHA-256 hash into the block header (the seed that
solved the puzzle is already in the block). Sign the hash and insert this signature
into the block header as well.

<p><li> If the blockchain has not been modified, then prepend the new
verified block to the local blockchain and multicast the new blockchain to all
other peers. Otherwise, if the blockchain has been modified, and the blockID of
UB is not in the blockchain, then mark UB as unverified and start over again
(with a new sequential block number) to verify it.

<p><li> Process 0 writes out the new version of BlockchainLedger.json every
time it gets an updated blockchain (that is, it replaces to old disk version
of the current blockchain).

<p><li> After a successful run of your program, copy BlockchainLedger.json to
BlockchainLedgerSample.json to turn in with your program.
</ul><p>

<strong> Congratulations! You are done with the basic blockchain
assignment. </strong>


<br> <br>
<hr>
<hr>
<hr><p><p>

<h2> Other functionality you can write, and which you <i> must </i>
  understand: </h2>



<h3> Verifying the entire blockchain </h3>

For our purposes, ignore the first block in the blockchain which
is a dummy entry of your own design and the same for all processes.<p>

Starting with block 2, check all blocks in the blockchain, as
follows:<p>

<ul>

<p><li> We simply re-verify all the blocks in the chain, one after
another. But we don't have to solve the puzzles, because all of the random
seed strings that have solved each respective puzzle are already stored in
the blocks themselves.

<p><li> Concatenate the current block data (not the header) with the SHA-256 value
from the previous block. Produce an SHA-256 hash. Verify that the
Proof-of-Work SHA-256-String in the current block's header matches the hash just produced.

<p><li> Verify that the hash just created solves the puzzle.

<p><li> Validate the Signed-SHA56 signature using the public key of
the verifying process.

<p><li> Validate the Signed-BlockId using the public key of the
creating process.

<p><li> [hard and completely optional] If this is the creating process of the record,
check the DataHash value against local archive records to see if the
data has been altered, indicating a leaked secret key.

</ul>


<hr>

<h3> Functionality / console commands: </h3>

On each console, display the possible commands you provide, and enter an
input loop to accept, e.g., the following commands:<p>

C&mdash;Credit. Loop through the blockchain and keep a tally of which
process has verified each block. Display the results on the console.<p>


R&mdash;Read a file of records to create new data. Process 0 should re-write the
BlockchainLedger.json file each time the blockchain is re-created.<p>

V&mdash;Verify the entire blockchain and report errors if any. (Several
forms). Note: unless you have errors, this is not likely to report anything
other than success. See the samples below, but you can have some latitude on
how your verification works. There are several forms of verification, each
looking for something different. <p>

L&mdash;On a single line each list block num, timestamp, name of patient,
diagnosis, etc. for each record.<p>


For bragging rights you can extend the range of commands beyond this
short list if you wish. Give a description of the commands (1) at the top
of your source code, (2) listed on the console at startup, and (3) in
the comments at the bottom of your checklist.<p>


<hr>

<pre>

On initialization, acknowldege receipt of another process's public key with
a message on the console of the receiver.

[Upon receipt of a public key from another process keep the process ID
(0, 1, 2) in a table along with the public key.]

Acknowledge receipt of a new blockchain.

<hr>

Console input format when reading additional records from a file:

<i> R filename </i>

[Format: First-name Last-name DOB SSNUM Diagnosis Treatment Prescription]

Example:<p>

> R MyBlockData.txt

<i> 5 records have been added to unverified blocks. </i>

<hr>

Example of listing output. List by position in the blockchain in
order (latest first), and include the timestamp:

> L

[N-14] etc.
13. 2017-09-01.14:12:06 Sarah Lewis 1998.07.04 123-456-888 infection penicillin
12. 2017-09-01.10:26:35 John Smith 1996.03.07 123-456-789 pneumonia BedRest aspirin
11. 2017-08-31.11:22:01 Joseph Ng 1995.06.22 987-65-4321 measels BedRest aspirin
[10-0] etc.

<hr>

Example of a credit request:

> C

Verification credit: P0=25,P1=60, P3=15

<hr>

Example of some verification requests (you can use your own
techniques). Note that you are not likely to have errors in the blockchain,
so you might have to just put a note that you are faking the error output:

> V

Blocks 1-100 in the blockchain have been verified. 

> V threshold

Blocks 1-60 have been verified
Block 61 invalid: SHA256 confirmed, but does not meet the work threshold
Blocks 62-100 follow an invalid block

or 

> V hash

Blocks 1-60 have been verified
Block 61 invalid: SHA256 hash does not match.
Blocks 62-100 follow an invalid block

or 

V signature

Blocks 1-60 have been verified
Block 61 invalid: signature does not match the verifying process
Blocks 62-100 follow an invalid block

or [optional]

V datahash

Blocks 1-60 have been verified
Block 61 invalid: Signature verified, but DataHash field does not match local records. Secret key exposed?
Blocks 62-100 follow an invalid block

</pre>

<hr>

<h3> Constraints: </h3>

<ul>

<p><li> Make sure that your program runs with the simple supplied automated
batch files. You will need to be careful about multicasting public keys, and
sending out unverified blocks only after all three processes are up and
running ready to receive them. Use (short!) sleep() calls if necessary to get everything
started and running smoothly. <p>

<p><li> You must follow startup and input specifications precisely, partially
so that we can grade your program using scripts. When Process 2 starts up it
triggers the rest of the system.

<p><li> For this assignment we will use three processes on a single
machine, with "localhost" assumed as the IP portion of the endpoint
for each server.

<p><li> Use the given JSON-based ledger as a starting point, and do your
    best to produce compliant JSON

<p><li> Use printable Strings as ledger representations for your
signatures and SHA-256 hash values. Convert as necessary in your
program code. Refer to the base-64 encoding and other translations
techniques given in the examples.

<p><li> For pedagogical simplicity, once any of the three processes
has stopped, the system becomes undefined, so there
is no need to store public/secret keys in a persistent way.

<p><li> For this assignment we will assume that we are using a reliable but
un-ordered protocol. We can assume that blockchains, public keys,
unverified blocks, and etc. will arrive at all nodes in the multicast
group in a reasonable time after they are sent. However order is not guaranteed.

<p><li> We don't care about the data, and we want testing to be easy. We will
use as many tokens as appear on the single line of input. Each field
is a single word. Tokens are delimited by spaces. For all trailing
blank tokens just insert XX into the record. The data I have provided fills
in a complete record with each line.

</ul>

<h2> Pseudocode </h2>

Here is some partical pseudo code for <i> one </i> kind of implementation. (Note that
you can use your own design, as long as it works.) This will help with some
timing problems at startup. In particular, you don't want to multicast data
to a process that is not yet listening. For each of the three processes: <p>

<ol>
<li> Spawn off three sub-threads (like the AdminLooper thread) to listen for
incoming (a) public keys, (b) unverified blocks and (c) updated blockchains.
<li> Sleep for ~2 seconds in your main thread so that the listeners for
all the processes become active before you continue.
<li> Now, in your main thread, multicast your public key, read in lines of data,
multicast them as unverified blocks to the group, do the work of verifying
blocks, and etc.
</ol>

<h2> Making the assignment easier </h2>

You might lose some points, but the assignment can be made
significantly easier with still substantial credit. I strongly
encourage you to consider adding the missing functionality only
after you have the basic blockchain system running. <p>

<ul>

<p><li> Don't worry about providing [all the] console commands. Just run on
the given input file for each process.

<p><li> Start out using the simplest method for marshaling your data from one
process to antoher. Add the JSON external data format later.

<p><li> Don't worry about verifying the whole blockchain.

<p><li> Don't worry about verifying any signatures. If you leave out ALL the
code having to do with digital signatures, all of the blockchain code will still
run the same.

<p><li> Don't worry about signing either the SHA-256 value (verifying
process) or the BlockID (creating process).

<p><li> But ALWAYS provide good comments in your code showing you understand
what you have done.

<p><li> Get the basic assignment done to coordinate your three processes,
and produce an JSON blockchain for most of the credit. Then add the rest of the functionality in
this list.
</ul>

<hr>


<h2> Blockchain updates / Collisions and blockchain forks (Optional and
Hard!)</h2>

<h3> Work done in this section is for extra credit. You must <i> read
</i> the notes to prepare for being tested on the concepts, but programming
the following is not required. </h3>

Depending on the protocol used, updated blockchains and unverified blocks may arrive in any order, and
duplicates may arrive as well. Because of this, different versions
of a verfied block may be created, and the blockchain may
fork. In this case we will need to pick a winner and discard the
loser. Sometimes we may need to re-multicast some of the blocks from
the loser as once again unverified.<p>

When you receive a new blockchain, place it in a queue of blockchains
to be processed. Compare each successive queue entry to the existing
blockchain, always replacing the existing blockchain with the winner.<p>

<i> Identical blocks </i> means that the SHA-256 hash value in the
headers of both blocks is the same.<p>

<h3> Normal update conditions: </h3>

In most cases the new blockchain will gracefully replace the old
blockchain because the old blockchain will be a perfect subset of the
new blockchain. That is, the new blockchain will be comprised of the
old blockchain with a new block prepended to the front of it. In this
case there is no forking of the blockchain.<p>

<h3> Collision conditions: </h3>

SAME LENGTH BLOCKCHAIN:<p>

Always work back to the earliest identical block in both blockchains. Then, for successors
to that block, identify the comparison condition:<p>

<ol>

<li> Both blockchains are the same&mdash;discard one (this situation could
happen depending on the reliability of your protocol). This will
be true when the latest block of each blockchain is identical.<p>

<p><li> The same blockID appears at same sequence number, but with different
timestamps&mdash;the block with the lower timestamp wins. For each
succeeding block in the loser blockchain, if it doesn't appear in the
winner blockchain then re-multicast the block as
unverified. (Timestamps are created locally so we won't actually know
which block was created first.)

<p><li> Different blockID at the same sequence number&mdash;the block with the lower timestamp wins. For this
block in the loser and all succeeding blocks in the loser, if the block does not
appear in the winner blockchain then re-multicast each block as unverified.

</ol>

DIFFERENT LENGTH BLOCKCHAIN:<p>

The longest blockchain wins.<p>

[Optional] Verify the entire longest blockchain.<p>

Work back in both blockchains to the latest identical block. Then, for each successor
to that (identical) block in the loser: If the blockID does not appear
in the longer (winner) chain, then re-multicast the block as
unverified. If you are clever, you can handle this in the same code
as for normal updates to the blockchain. That is, this same condition
would apply to a normal update, except that in that case there are no blocks appearing
in the shorter (loser) chain after the lastest identical block. <p>

<hr>

<h2> Bragging rights: </h2>

<ul>

<p><li> Add your own functionality (that does not interfere with grading, as
above) and tell us about it in your header comments, your checklist,
and on the console at startup. Use arguments 2 through N to the
program to trigger extra functionality as needed.<p>

<p><li> Provide a written analysis (with clear examples) of
vulnerabilities in the blockchain technology / design of the program
as we have implemented it here. In particular: if you were a badly
behaved node, how could you insert a counterfeit record into the
ledger? (Attach as HTML to the bottom of the checklist.)

<p><li> For security experts, provide a writen analysis of
vulnerabilities in the cryptographic technology as we have used it
here, under scrutiny of the latest worldwide developments. For
example, if we didn't require that the blockIDs be signed, then if
there were very few real unverified blocks being multicast, a malicious
node would have time to create a string of counterfeit blocks while
pretending that the original unverified blocks were lost on the
network. (Attach as HTML to the bottom of the checklist.)

<p><li> Hard! When verifying produces an invalid block, multicast a
blockchain error and the truncated blockchain (back to before the
error), report the bad block and the bad process (the assumption is
that the block has been counterfeited and the record in it should not
be added back to the chain), and re-multicast all of the succeeding
blocks as unverified, to restore the blockchain minus the bad
block.<p>

<p><li> Very hard! Implement any number of peers in a multicast group
based on a DHT, with persistent public key / private key pairs so that processes can come
and go at will.
</ul>

<h2> Notes: </h2>

<ul>

<p><li> Simplification note: In a typical system, many records would be grouped into a block for
verification. For simplicity, we use one record (ledger entry) per
block.

<p><li> We assume that there is a prohibitive penalty for any node
exposing its secret key. However, even if a secret key has been
exposed, all records in the blockchain belonging to the offending
process timestamped prior to the known loss, and all records not
created or verified by the offending process may still be valid in the
ledger. That is, even if a malicious process were to obtain a secret
key, it is not possible to go back and insert new records into the
blockchain without invalidating all subsequent blocks. (And this is
the beauty of the blockchain structure!)

<p><li> For this assignment we will use three processes on a single
machine. In practice each endpoint would more typically be located on
a unique network-connected machine. Because these local processes operate through
ports as endpoints with "localhost" assumed, it is trivial to extend
this code to run on multiple machines by simply replacing the IP
addresses in the endpoint when the port connections are set up. For fun
you should try your code on multiple machines.

<p><li> Depending on how group membership is handled, it would be
possible for a malicious process to multicast counterfeit unverified
records to the blockchain group. However, this will be detected when
the public key of the creating process is used to verify the signed
blockID.

<p><li> To ensure good behavior of all processes, nodes should be
given valuable "credit" for solving the puzzles and verifying
blocks. Without work-based verification the blockchain system will
have security risks. No one node should dominate in the
verifications. Credit could take the form of outright payments by
other participants, or ? Or negative reinforcement could be used by
threats of being expelled from the shared ledger system for nodes that
do not verify their share of the unverified blocks.

<p><li> <strong> DataHash Field </strong> [Optional] After the
creating process places the hash value (of the input data portion
only) in the DataHash field of the block, this value is also saved in
a local table for auditing purposes. If the secret key of the process
is later suspected of being exposed, the data portion in all records
in the blockchain created by this process can be re-hashed and
compared against the locally stored hash values to see if a record has
been altered. Only altered records would be invalidated. This would
also help to identify the time the secret key was absolutely known to
already have been exposed.<p>

There are several ways to handle this invalidation. For example (1)
all blocks containing such records could be removed, and all
subsequent blocks re-multicast as unverified, so that the blockchain
is rebuilt. (2) A second blockchain of invalid records could be
created and always reviewed prior to using a record in the original
blockchain. (3) The local creating process (whose secret key was
exposed) could notify other processes that the problem would be
handled locally and other processes would thereafter assume that all
records created by this process in the blockchain were invalid during
a particular interval.

<p><li> <strong> Variations </strong> There are many variations of the
basic blockchain idea. For example, a
patient's records can all be encrypted in the blockchain such that
they must approve access to any of their records. In this way all
providers in the group can always have a local copy of the patient's
records, but can only access them as approved by the patient. This
solves the maddening and dangerous problem of trying to get one's
medical records transfered from one office to another after filling out large piles of HIPAA
forms and arranging for faxes and snail mail.<p>



Here is an idea of how this could work:<p>

<ol>

<li><p> All provider nodes have the entire ledger kept locally, but the
patient records are all encrypted with the patient's public key.

<li><p> Patient P requests MD-A send records to MD-B.

<li><p> MD-A notifies MD-B which records are appropriate. MD-B sends the
records to P. [Using client software and his known password] P retrieves his
secrety key, decrypts the records with his secret key, then encrypts them
again with the public key of MD-B, and sends the decrypted records back to
MD-B.

<li><p> MD-B decrypts the record with her private key.

</ol>

In this example communication about the medical records from MD-A and MD-B
is strictly about record numbers and contains no content. P has complete
control over his medical records. NO RECORDS HAVE TO BE SENT because MD-B
already has the records stored locally at the provider site (or through a
coorperative local service).

</body></html>
