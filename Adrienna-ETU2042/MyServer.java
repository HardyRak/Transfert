package server; 
import java.net.*;  
import java.io.*;  
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

 public class MyServer 
    {  
        private static DataOutputStream dataOutputStream= null;
        private static DataInputStream dataInputStream= null;

        public static void main(String[] args){
                    while (true) {
                        try(ServerSocket serverSocket = new ServerSocket(5000)){
                            System.out.println("listening to port:5000");
                            Socket clientSocket=serverSocket.accept();
                            System.out.println(clientSocket+":connected");
                            dataInputStream=new DataInputStream(clientSocket.getInputStream());
                            dataOutputStream=new DataOutputStream(clientSocket.getOutputStream());
                            
                            String rd = dataInputStream.readUTF();
                            System.out.println(rd);
                            receiveFile(rd);

                            dataInputStream.close();
                            clientSocket.close();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ie) {
                            System.err.println("Interruption");
                        }

                    
                    }
        }

        private static void receiveFile(String fileName)throws Exception{
            int bytes=0;
            FileOutputStream fileOutputStream=new FileOutputStream(fileName);

            long size=dataInputStream.readLong(); //read file size
            byte[] buffer=new byte[4*1024];
            while(size>0 && (bytes = dataInputStream.read(buffer,0,(int)Math.min(buffer.length,size))) != -1) {
                fileOutputStream.write(buffer,0,bytes);
                size -= bytes; //read up to file size
            }
            fileOutputStream.close();
        }
    }  