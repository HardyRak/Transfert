package client; 
import java.io.*;
import java .net.Socket;
public class MyClient{
    private static DataOutputStream dataOutputStream=null;
    private static DataInputStream dataInputStream=null;

    String nom;
    String path;

    public void setNom(String nom){
        this.nom=nom;
    }
    public String getNom(){
        return this.nom;
    }
    public void setPath(String path){
        this.path=path;
    }
    public String getPath(){
        return this.path;
    }

    public MyClient(String path,String nom){
        this.setPath(path);
        this.setNom(nom);
    }
    public boolean verif(){
        try(Socket socket=new Socket("localhost",5000)){

            dataInputStream=new DataInputStream(socket.getInputStream());//recevoir
            dataOutputStream=new DataOutputStream(socket.getOutputStream());//envoyer
            String g=this.getPath();
            System.out.println("path:"+g);
            System.out.println("nom:"+this.getNom());
            dataOutputStream.writeUTF(this.getNom());
            sendFile(g);
            dataInputStream.close();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
             return false;
        }
    }

    private static void sendFile(String path) throws Exception{
        int bytes=0;
        File file=new File(path);
        FileInputStream fileInputStream=new FileInputStream(file);

        //send file size
        dataOutputStream.writeLong(file.length());
        //break file into chunks
        byte[] buffer=new byte[4*1024];
        while((bytes=fileInputStream.read(buffer))!=-1){
            dataOutputStream.write(buffer,0,bytes);
            dataOutputStream.flush();
        }
        System.out.println("Receive file");
        fileInputStream.close();
    }

}