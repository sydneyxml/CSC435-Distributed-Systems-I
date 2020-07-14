/*--------------------------------------------------------

1. Name / Date:

Ximan Liu
2020.5.27

2. Java version used, if not the official version for the class:

Java 8

3. Precise command-line compilation examples / instructions:

> javac -cp "gson-2.8.2.jar" *.java

or

> javac -cp "gson-2.8.2.jar" Blockchain.java


4. Precise examples / instructions to run this program:

In separate shell windows:
// for MacOS
> java -cp ".:gson-2.8.2.jar" Blockchain 0
> java -cp ".:gson-2.8.2.jar" Blockchain 1
> java -cp ".:gson-2.8.2.jar" Blockchain 2

5. List of files needed for running the program.

 a. Blockchain.java
 b. BlockchainLog.txt
 c. BlockInput0.txt
 d. BlockInput1.txt
 e. BlockInput2.txt
 f. BlockchainLedgerSample.json
 g. checklist-block.html


6. Notes:

This file will produce JSON file and will marshall the record over a socket.

----------------------------------------------------------*/


import java.net.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.StringReader;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.*;
import java.util.*;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.Base64;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.text.*;

import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.xml.bind.DatatypeConverter;

import java.io.*;
import java.io.StringWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.Reader;


//class for unverified block
class UB extends Thread {
 public Socket socket;
 // constructor
 UB(Socket socket) {this.socket = socket;}

 public void run() {
     try {
         // set up input from UB
    	 BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
         // initialize
         String nb = "";
         // catch 
         String content;           
         // while loop when content is not null
         while ((content = input.readLine()) != null) {
         	nb += content;
         }
         // prior queue
         Blockchain.brq.put(Tools.remodifyrecord(nb)); 
         // close socket
         input.close();
         this.socket.close();
     } catch (Exception x) {
         Blockchain.errorprinter("Error!", x);
         }
     }
 }


//class unverified block thread
class ubthread implements Runnable {
 // constructor
	public int porttouse;
	ubthread(int porttouse) {this.porttouse = porttouse;}

 public void run() {
 	// number of order
     int length = 15;
     // set up socket
     Socket socket;

     try {
     	// set up serversocket with port and length
         ServerSocket serversocket = new ServerSocket(porttouse, length);
         
         // forever true loop
         while (true) {
             // build up serversocket connection
             socket = serversocket.accept();
             // start
             new UB(socket).start();
             }
         } catch (IOException x) {
         Blockchain.errorprinter("Error!", x);
         }
     }
 }


//class blockchain thread
class bcthread implements Runnable {
 // constructor
	public int portforuse;
	bcthread(int portforuse) {this.portforuse = portforuse;}

 public void run() {
 	// number of order
     int length = 15;
     // set up socket
     Socket socket;

     try {
     	// initialize serversocket with port and length
         ServerSocket serversocket = new ServerSocket(portforuse, length);
         
         // forever true
         while (true) {
             // build up serversocket connection
             socket = serversocket.accept();
             // start
             new BC(socket).start();
         }
     } catch (IOException x) {
         Blockchain.errorprinter("Error!", x);
         }
     }
 }


//class public key thread
class pkthread implements Runnable {
 // constructor
	public int portpk;
	pkthread(int portpk) {this.portpk = portpk;}

 public void run() {
 	// number of order
     int length = 15;
     // set up socket
     Socket socket;

     try{
     	// set up serversocket with port and length
         ServerSocket serversocket = new ServerSocket(portpk, length);
         
         while (true){
         	// build up serversocket connection
             socket = serversocket.accept();
             // start
             new PK(socket).start();
             }
         } catch (IOException x) {
         Blockchain.errorprinter("Error!", x);
         }
     }
 }


// produce, signature, and check keys
class Key {
	// initialize with null
    private KeyPair kp = null;
    private PublicKey publickey = null;
    private PrivateKey privatekey = null;
    private Signature sign = null;
    
    // public key
    public Key(PublicKey publickey) {
    	String sig = "SHA1withRSA";
    	try {
    		this.sign = Signature.getInstance(sig);
    	} catch (NoSuchAlgorithmException x) {
    		Blockchain.errorprinter("Error!", x);
    	}
    	// public key constructor
    	this.publickey = publickey;
    }
 
    public Key() {     
    	String sig = "SHA1withRSA";
    	try {
    		// backup private key
            this.sign = Signature.getInstance(sig);
    	} catch (NoSuchAlgorithmException x) {
    		Blockchain.errorprinter("Error!", x);
    	}
    }
   
    // create key pair
    public void createkp(long randseed) {
        String enAlg = "RSA";
        String hashAlg = "SHA1PRNG";
        String hashAlgSupport = "SUN";
        try {
        	// be called in the final step
        	// backup private key
        	KeyPairGenerator kpg = KeyPairGenerator.getInstance(enAlg);
        	SecureRandom sr = SecureRandom.getInstance(hashAlg, hashAlgSupport);
        	sr.setSeed(randseed);
        	kpg.initialize(1024, sr);
        	this.kp = kpg.generateKeyPair();
        	this.publickey = kp.getPublic();
        	this.privatekey = kp.getPrivate();    
        } catch (NoSuchAlgorithmException x) {
        	Blockchain.errorprinter("Error!", x);
        	} catch (NoSuchProviderException x) {
        		Blockchain.errorprinter("Error!", x);
        		}
    }
    
    // get public key
    public PublicKey Getpublickey() {
        return this.publickey;
    }

    public byte[] signbyte (byte[] data) {
    	try {
    		this.sign.initSign(this.privatekey);
    		this.sign.update(data);
    		return this.sign.sign();
    	} catch (SignatureException x) {
			Blockchain.errorprinter("Error!", x);
			return null;
		}
    	catch (InvalidKeyException x) {
			Blockchain.errorprinter("Error!", x);
			return null;
		}
    }
    
    // check whether data are the same before and after signed
    public boolean verifySig(byte[] data, byte[] sigdata) {
    	try {
            this.sign.initVerify(this.publickey);
            this.sign.update(data);
            return (this.sign.verify(sigdata));
        }
    	catch (SignatureException x) {
    		Blockchain.errorprinter("Error!", x);
    		return false;
    	}
    	catch (InvalidKeyException x) {
    		Blockchain.errorprinter("Error!", x);
    		return false;
    	}
    }
}


//class thread reader
class threadreader implements Runnable {
 // initialize port
	public int porttr = 6000;

 public void run() {
 	// number of order
     int length = 15;
     // set up socket
     Socket socket;

     try {
     	// set up serversocket with port and length
         ServerSocket serversocket = new ServerSocket(porttr, length);
         
         // set up key and key pair
         Key Key = new Key();
         Key.createkp(1000);

         // send public key to processor
         Tools.setkey(Key);
         Tools.sendkey(port.getkspInUse());
         


         while (true) {
         	// build up serversocket connection
             socket = serversocket.accept();
             // start
             new KeyHelper(socket, Key).start();
         }
     } catch (IOException x) {
         Blockchain.errorprinter("Error!", x);
         }
     }
 }


// class port
class port {
	// initialize port
	public static int Kport = 4710;
	public static int UBport = 4820;
	public static int BCport = 4930;
	public static final int KeyPort = 6000;
	
	public static int advKport;
    public static int advUBport;
    public static int advBCport;
	
	// constructor
    public static int[] KportUse = new int[Blockchain.processor];
    public static int[] UBportUse = new int[Blockchain.processor];
    public static int[] BCportUse = new int[Blockchain.processor];

    // mark the port in processing
    public static void setport(int process) {
        for (int i = 0; i < process; i++) {
            KportUse[i] = Kport + i;
            UBportUse[i] = UBport + i;
            BCportUse[i] = BCport + i;
        }
    }

    // set port for increasing PID to base
    public static void settask(int PID) {
        advKport = PID + Kport;
        advUBport = PID + UBport;
        advBCport = PID + BCport;
    }
    
    // get key server port
    public static int getksp() {
        return advKport;
    }
    // get unverified block server port
    public static int getubsp() {
        return advUBport;
    }
    // get block chain server port
    public static int getbcsp() {
        return advBCport;
    }
    // get key server port in use
    public static int[] getkspInUse() {
        return KportUse;
    }
    // get unverified block server port in use
    public static int[] getubspInUse() {
        return UBportUse;
    }
    // get Blockchain port in use
    public static int[] getbcspInUse() {
        return BCportUse;
    }
}


// class data
class data {	
	// set up string
    String FName = "";
    String LName = "";
	String SSNum = "";
    String DOB = "";
    String Diag = "";
    String Treat = "";
	String Rx = "";

    // get first name
    public String getFName() {
        return this.FName;
    }
   // set first name
    public void setFName(String FName) {
        this.FName = FName;
    }
    // get last name
    public String getLName() {
        return LName;
    }
    // set last name
    public void setLName(String LName) {
        this.LName = LName;
    }
    // set SSN
    public void setSSNum(String SSNum) {
        this.SSNum = SSNum;
    }
    // set date of birth
    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
    // set diag
    public void setDiag(String Diag) {
        this.Diag = Diag;
    }
    // set treat
    public void setTreat(String Treat) {
        this.Treat = Treat;
    }
    // set Rx
    public void setRx(String Rx) {
        this.Rx = Rx;
    }
}


// blockchain field
class blockrecord implements Comparable<blockrecord>, Comparator<blockrecord> {
	// constructor
    public data data = new data();
    public int blocknum;
    public String hashedSHA256data;
    public String signedSHA256data;
    public Date temptime = new Date();
    public String BlockID;
    public String signBID;
    public String VPID;
    public String process;
    public String prevhash;
    public String seed;
    
    // get data
    public data getdata() {
        return this.data;
    }
    // get block number
    public int getblocknum() {
        return blocknum;
    }
    // set block number
    public void setblocknum(int blocknum) {
        this.blocknum = blocknum;
    }
    // get hashed data
    public String usehashedSHA256data() {
        return hashedSHA256data;
    }
    // set hashed data
    public void sethashedSHA256data(String hashedSHA256data) {
        this.hashedSHA256data = hashedSHA256data;
    }
	// get signed data
    public String getsignedSHA256data(){
        return signedSHA256data;
    }
    // set signed data
    public void setsignedSHA256data(String signedSHA256data){
        this.signedSHA256data = signedSHA256data;
    }
    // get previous hash
    public String getprevhash() {
        return this.prevhash;
    }
    // set previous hash
    public void setprevhash(String prevhash) {
        this.prevhash = prevhash;
    }
    // get value
    public String getseed() {
        return this.seed;
    }
    // set value
    public void setseed(String seed) {
        this.seed = seed;
    }
    // set process
    public void setprocess(String process) {
        this.process = process;
    }
    // set verify PID
    public void setVPID(String VPID) {
        this.VPID = VPID;
    }
    // get block id
    public String getBlockID() {
        return this.BlockID;
    }
    // set block id
    public void setBlockID(String BlockID) {
        this.BlockID = BlockID;
    }
    // get signed block id
    public String getsignBID() {
        return this.signBID;
    }
    // set signed block id
    public void setsignBID(String signBID) {
        this.signBID = signBID;
    }

    // compare block record
    @Override
    public int compareTo(blockrecord br) {
        return this.temptime.compareTo(br.temptime);
    }
    // difference between block record
    @Override
    public int compare(blockrecord m, blockrecord n) {
        return (m.blocknum - n.blocknum);
    }
}


// class Tools singleton pattern
class Tools {
	// constructor
    public static Key Key = null;
    public static Key getkey() {return Key;}
    public static void setkey(Key key) {Key = key;}

    // read into content and switch into a block record
    public static ArrayList<blockrecord> arrinput(String filename, int PID) {
        ArrayList<blockrecord> blockrecord = new ArrayList<blockrecord>();
        String currPID = Integer.toString(PID);

        // read input content
        try (BufferedReader bufferreader = new BufferedReader(new FileReader(filename))) {
            // local variable
        	String input;
            while ((input = bufferreader.readLine()) != null) {
                // block record
            	blockrecord Record = new blockrecord();
                // head
                Record.setBlockID(new String(UUID.randomUUID().toString()));
                Record.setprocess(currPID);
                // split input content
                String[] inputline = input.split(" +");
                // personal information
                Record.getdata().setFName(inputline[0]);
                Record.getdata().setLName(inputline[1]);
                Record.getdata().setDOB(inputline[2]);
                Record.getdata().setSSNum(inputline[3]);
                Record.getdata().setDiag(inputline[4]);
                Record.getdata().setTreat(inputline[5]);
                Record.getdata().setRx(inputline[6]);
                // add record
                blockrecord.add(Record);
            }
        }
        catch (Exception x) {
            Blockchain.errorprinter("Error!", x);
        }
        // print out
        System.out.println(blockrecord.size() + " blockrecord.");
        System.out.println("Content names: ");
        for (blockrecord record : blockrecord) {
            System.out.println("\t" + record.getdata().getFName() + " " + record.getdata().getLName());
        }
        return blockrecord;
    }

    // modify record
    public static String modifyrecord (ArrayList<blockrecord> blockrecord) {
        Gson gs = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        return gs.toJson(blockrecord);
    }

    // modify data
    public static String modifydata (data data) {
        Gson gs = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        return gs.toJson(data);
    }

    // modify a block record
    public static String modifyrecord (blockrecord blockrecord) {
        Gson gs = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        return gs.toJson(blockrecord);
    }

    public static blockrecord remodifyrecord (String record) {
        return new Gson().fromJson(record, blockrecord.class);
    }
    
    //modify blockrecord ledger
    public static ArrayList<blockrecord> ledger (String ledger) {
		Type t = new TypeToken<ArrayList<blockrecord>>(){}.getType();
		return new Gson().fromJson(ledger, t);
	}

   
    // send unverified block to key
    public static void sendmessage (blockrecord ub){
        try {      	
        	// create socket
            Socket socket = new Socket(Blockchain.servername, port.KeyPort);
            PrintStream ps = new PrintStream(socket.getOutputStream());
            // start all processors
            if (ub == null) {
            blockrecord br = new blockrecord();
           	// head
           	br.setBlockID("0");
           	br.setprocess(Integer.toString(Blockchain.PID));
           	// sign record to key
            ps.println(modifyrecord(br));
            // clear flush
            ps.flush();
            }
            else {
            	// sign record to key
                ps.println(modifyrecord(ub));
                // clear flush
                ps.flush();
            }
            // close socket
            ps.close();
            socket.close();
        } catch (IOException x) {
        	Blockchain.errorprinter("Error!", x);
        }
    }

    
    // send public key to Key
    public static void sendkey(int[] advKport) {
    	// send key to processor
        for (int i = 0; i < advKport.length; i++) {
            try {
            	// set up socket
                Socket socket = new Socket(Blockchain.servername, advKport[i]);
                // pass message to server
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(Key.Getpublickey());
                // clear flush, close socket
                System.out.flush();
                output.close();
                socket.close();
            }
            catch (IOException x) {
                Blockchain.errorprinter("Error!", x);
                return;
            }
        }
    }

    
    // set up hash and get value
    public static byte[] usehash(String usehash) {
        try {
            MessageDigest message = MessageDigest.getInstance("SHA-256");
            // get value
            return message.digest(usehash.getBytes("UTF-8"));
        }
        catch (Exception x){
            Blockchain.errorprinter("Error!", x);
            return null;
        }
    }
}


class ubcthread implements Runnable {
    public void run() {
    	blockrecord br;
    	try {
        	// forever true
            while (true) {
            	// initialize with false
                boolean problem = false;
                // blockrecord in queue
                br = Blockchain.brq.take();
                Blockchain.portname("Unverified Block Updated with New Unverified Block: " + Tools.modifyrecord(br));

                for (blockrecord ledger : Blockchain.blockledger) {
                	// see whether there is block
                    if (ledger.getBlockID().compareToIgnoreCase(br.getBlockID()) == 0) {
                        problem = true;
                        break;
                    }
                }
                if (!checkVB(br)) {continue;}

                try {
                    // Set up block num
                    int currblocknum = Blockchain.blockledger.size() + 1;
                    br.setblocknum(currblocknum);
                    // Set up VPID
                    br.setprocess(Integer.toString(Blockchain.PID));

                    String prevhash;

                    // set up prevhash from block
                    if (Blockchain.blockledger.size() != 0 && !br.getBlockID().equals("0") ) {
                        int prevblocknum = Blockchain.blockledger.size() - 1;
                        // value of prevhash
                        prevhash = Blockchain.blockledger.get(prevblocknum).getprevhash();
                    }
                    else{
                        prevhash = DatatypeConverter.printHexBinary(Tools.usehash(br.usehashedSHA256data()));
                    }

                    for (int i = 1; i < 200; i++) {
                        // create random string of number and add to block
                        String rs = this.randnum(15);
                        // connection
                        String connection = rs + prevhash;
                        // set up hash
                        String setuphash = DatatypeConverter.printHexBinary(Tools.usehash(connection));

                        if (this.judgeanswer(setuphash)) {
                            // update seed
                            br.setseed(rs);
                            // update new hash
                            br.setprevhash(connection);
                            break;
                        }
                        // for loop ledger
                        for (blockrecord ledger : Blockchain.blockledger) {
                        	// whether there is a block
                            if (ledger.getBlockID().compareToIgnoreCase(br.getBlockID()) == 0) {
                                problem = true;
                                break;
                            }
                        }
                        if (problem) {break;}	// break when necessarily
                    }
                } catch (Exception x) {
                    Blockchain.errorprinter("Error!", x);
                }
                if (!problem){	// if there is a block
                    for (blockrecord ledger : Blockchain.blockledger) {
                        if (ledger.getBlockID().compareToIgnoreCase(br.getBlockID()) == 0) {
                            problem = true;
                            break;
                        }
                    }
                    if (!problem) {	// add new record
                        Blockchain.blockledger.add(br);
                        this.sendVB();
                    }
                }
            }
        } catch (Exception x) {
            Blockchain.errorprinter("Error!", x);
        }
    }

 
    // judge whether deal with the puzzle
    public boolean judgeanswer(String ja) {
        try {
            // pick up 16 bits from hash and transfer to unsigned
            int workload = Integer.parseInt(ja.substring(0, 4), 16);
            if (workload < 5000) {
            	Blockchain.portname("Puzzle anwswer: " + workload);
            	return true;
            }
        }
        catch (IndexOutOfBoundsException x) {
            Blockchain.errorprinter("Error!", x);
            return false;
        }
        return false;
    }

    // create random numeric string
    private String randnum(int randnum) {
    	// initialize
        String alphanum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        // constructor
        StringBuilder build = new StringBuilder();
        // force seed to 0
        while (randnum-- != 0) {
            // char index
            // count = 0
            int ch = (int)(Math.random() * alphanum.length());
            build.append(alphanum.charAt(ch));
        }
        // convert to string
        return build.toString();
    }

    // update ledger
    public void sendVB(){
        PrintStream ps;
        Socket socket;
        try {
            // new block
            int[] advBCport = port.getbcspInUse();
            for (int i = 0; i < advBCport.length; i++) {
                // pass processor
                socket = new Socket(Blockchain.servername, advBCport[i]);
                ps = new PrintStream(socket.getOutputStream());
                ps.println(Tools.modifyrecord(Blockchain.blockledger));
                // clear flush
                ps.flush();
                ps.close();
                socket.close();
            }
        }
        catch (IOException x) {
            Blockchain.errorprinter("Error!", x);
        }
    }

    // check signed BID, prevhash and data
    private boolean checkVB(blockrecord br) {
        // verify signed BID
        try {
            byte[] signBID = Base64.getDecoder().decode(br.getsignBID());
            // check signed blockID
            if (!Tools.getkey().verifySig(br.getBlockID().getBytes(), signBID)) {return false;}
        }
        catch (IllegalArgumentException x){
            Blockchain.errorprinter("Error!", x);
            return false;
        }

        // verify signed data
        try {
            byte[] VSD = Base64.getDecoder().decode(br.getsignedSHA256data());
            // check signed data ID
            if (!Tools.getkey().verifySig(br.usehashedSHA256data().getBytes(), VSD)) {return false;}
        }
        catch (IllegalArgumentException x) {
            Blockchain.errorprinter("Error!", x);
            return false;
        }
        return true;
    }
}


// set up key if doesn't exist public key
class PK extends Thread {
	// set up socket
    public Socket socket;
    PK(Socket socket) {this.socket = socket;}

    public void run() {
        // make sure it is process 2
        if (Blockchain.PID == 2) {return;}
        try {
            // set up channel
        	ObjectInputStream input = new ObjectInputStream(this.socket.getInputStream());
        	
            // local variable publickey
        	PublicKey publickey = (PublicKey) input.readObject();
        	    if (Tools.getkey() == null) {
                Tools.setkey(new Key(publickey));
            }
            // close
            input.close();
            this.socket.close();
        }
        catch (Exception x) {
        	Blockchain.errorprinter("Error!", x);
        } 
    }
}

// sign with secret key
class KeyHelper extends Thread {
    public Socket socket;
    public Key Key;
    // constructor
    KeyHelper(Socket socket, Key Key){
        this.socket = socket;
        this.Key = Key;
    }
    public void run() {
        try {
            // buffer reader input content
        	BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        	// initialize local variable
           	String before = "";
           	String after;
            while ((after = input.readLine()) != null) {
            	before += after;
            }
            // sign string and BID into new block with private key, pass to unverified block processor
            blockrecord brcontent = Tools.remodifyrecord(before);
            // create new hash for data
           	byte[] blockhash = Tools.usehash(Tools.modifydata(brcontent.getdata()));
           	StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < blockhash.length; i++) {
            	buffer.append(Integer.toString((blockhash[i] & 0xff) + 0x100, 16).substring(1));
            }
            // transfer to string
            String SHA256string = buffer.toString();
            brcontent.sethashedSHA256data(SHA256string);
            // sign data and base and encoding
           	byte[] sigBlock = this.Key.signbyte(brcontent.usehashedSHA256data().getBytes("UTF-8"));
            brcontent.setsignedSHA256data(Base64.getEncoder().encodeToString(sigBlock));
            // sign block ID and encoding
            byte[] signBID = this.Key.signbyte(brcontent.getBlockID().getBytes("UTF-8"));
            brcontent.setsignBID(Base64.getEncoder().encodeToString(signBID));
            // set up array
         	int[] UBP = port.getubspInUse();
         	// for loop for array
         	for (int i = 0; i < UBP.length; i++) {
         		// BlockID = 0 means it is passing to dummy block
         		if(brcontent.getBlockID().equals("0")) {
         			Blockchain.portname("Pass dummy block to processor" + i);
         		}
                else {
                	Blockchain.portname("Pass unverified block to processor " + i);
            	}
         		// set up socket UB serversocket
         		Socket UBserversocket = new Socket(Blockchain.servername, UBP[i]);
         		// output
         		PrintStream output = new PrintStream(UBserversocket.getOutputStream());
                output.println(Tools.modifyrecord(brcontent));
                // clear flush
                output.flush();
                // close channel and socket
               	output.close();
               	UBserversocket.close();
               	this.socket.close();
               	input.close();
                }
            }
            catch (IOException x) {
                Blockchain.errorprinter("Error!", x);
            }
        }
    }

// blockchain
class BC extends Thread {
	// constructor
    public Socket socket;
    public static final Lock Lock = new ReentrantLock();
    BC(Socket socket) {this.socket = socket;}

    public void run() {
        try{
        	// set up input buffer reader
        	BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        	// initialize local variable ledger
            String ledger = "";
            String afterledger = "";
            // readlines if it is not null
            while ((afterledger = input.readLine()) != null) {
            	ledger += afterledger;
            }
            // pass to ledger
            Blockchain.blockledger = Tools.ledger(ledger);
            // if PID is 0, write json
            if (Blockchain.PID == 0) {
            	this.exportledger();
            }
            // close socket and channel
            this.socket.close();
            input.close();
            }
            catch (Exception x) {
            	Blockchain.errorprinter("Error!", x);
            }
        }   

    private void exportledger() {
        try {
            Lock.lock();
            String tempblock = this.modifyrecord(Blockchain.blockledger);
            Lock.unlock();
            Blockchain.portname("ledger size: " + Blockchain.blockledger.size());
            // set up buffer writer
            BufferedWriter BW = new BufferedWriter(new FileWriter("BlockchainLedger.json", false));
            // write buffer
            BW.write(tempblock);
            // clear flush
            BW.flush();
            // close buffer writer
            BW.close();
        }
        catch (IOException x) {
            Blockchain.errorprinter("Error!", x);
        }
    }

    // set up block record
    private String modifyrecord(ArrayList<blockrecord> blockrecord) {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        return gson.toJson(blockrecord);
    }
}


//class Blockchain
public class Blockchain {
public static final boolean checkbug = true;
// block number is three
public static final int processor = 3;
// set up server name is localhost
public static final String servername = "localhost";
// set up queue for blockrecord
public static final BlockingQueue<blockrecord> brq = new PriorityBlockingQueue<blockrecord>();
// set up array list for blockledger
public static ArrayList<blockrecord> blockledger = new ArrayList<blockrecord>();
// initialize PID = 0
public static int PID = 0;


// main function body
public static void main(String args[]) {
	// set up string for reading insertion content
   String insertion;
   // set up processor for public keys. Also for blocks and blockchains
   port.setport(processor);

   // if user didn't mention PID, then it will be customized as 0
   if (args.length == 0) {
       System.out.println("Processor defaults setting to 0");
   }
   // else take args[0]
   else {
       PID = Integer.parseInt(args[0]);
   }

   // switch table for PID
   switch (PID) {
   	// case 1 is related to processor 1
       case 1: {
           insertion = "BlockInput1.txt";
           break;
       }
       // case 2 is related to processor 2
       case 2: {
           insertion = "BlockInput2.txt";
           // set up threadreader thread
           new Thread(new threadreader()).start();
           break;
       }
       // case default is related to processor 0
       default: {
           insertion = "BlockInput0.txt";
           break;
       }
   }

   // set port for processor 0, processor 1 and processor 2
   port.settask(PID);

   Blockchain.portname("Ports Start! ");
   System.out.println("\t Public Keys Port: " + port.getksp());
   System.out.println("\t Unverified Blocks Port: " + port.getubsp());
   System.out.println("\t Blockchain Port: " + port.getbcsp());
   Blockchain.portname("\n Insertion: " + insertion + "\n");

   // call thread for publickey and unverifiedblock
   try {
   	new Thread(new ubcthread()).start();
       new Thread(new pkthread(port.getksp())).start();
       new Thread(new ubthread(port.getubsp())).start();
       new Thread(new bcthread(port.getbcsp())).start();
       
       // sleep
       while (Tools.getkey() == null) {
           Thread.sleep(20);
           }
   } catch (Exception x) {
       Blockchain.errorprinter("Error!", x);
   }

   // senario when PID = 2
   if (PID == 2) {
       Tools.sendmessage(null);
   }

   // unverified block to processor
   ArrayList<blockrecord> rb = Tools.arrinput(insertion, PID);

   for (blockrecord br : rb) {
       Tools.sendmessage(br);
   }
}

// declare process 0, process 1 and process 2
public static void portname(String pn) {
	if (PID == 0) {
		System.out.println("Process 0" + Blockchain.PID + ": " + pn + "\n");		
	}    	
	if (PID == 1) {
		System.out.println("Process 1" + Blockchain.PID + ": " + pn + "\n");		
	}    	
	if (PID == 2) {
		System.out.println("Process 2" + Blockchain.PID + ": " + pn + "\n");		
	}
}

// error printer
public static void errorprinter(String pn, Exception x) {
	if (PID == 0) {
		System.out.println("Process 0" + Blockchain.PID + ": " + pn + "exception: " + x + "\n");		
	}    	
	if (PID == 1) {
		System.out.println("Process 1" + Blockchain.PID + ": " + pn + "exception: " + x + "\n");		
	}    	
	if (PID == 2) {
		System.out.println("Process 2" + Blockchain.PID + ": " + pn + "exception: " + x + "\n");		
	}
   x.printStackTrace();
}
}

