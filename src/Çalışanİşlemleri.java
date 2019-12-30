
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Çalışanİşlemleri {  // arayüzdeki işlemleri burdan gerçekleştircez
    
    private Connection con = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    
    public Çalışanİşlemleri(){
        /*         
         1. Class.forname ile jdbc driverı yükle
         2. cona drivermanagerdan getconnection ile bağla
        */
        String url = "jdbc:mysql://localhost/" + DataBase.db_ismi +"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");                                           
        } 
        catch (ClassNotFoundException ex) {
            System.out.println("JDBC JAR Dahil Edilmesi Gerekiyor....");
        }
        
        try {
            con = DriverManager.getConnection(url,DataBase.kullanıcı_adı,DataBase.parola);
            System.out.println("Bağlantı Başarılı....");
        } 
        catch (SQLException ex) {
            System.out.println("Bağlantı Başarısız...");
        }
        
    }
    
    public boolean girişYap(String kullanıcı_ad, String parola){
        
        String sorgu = "Select * From adminler where username = ? and password = ?";
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1, kullanıcı_ad);
            preparedStatement.setString(2, parola);
            ResultSet rs = preparedStatement.executeQuery(); 
            
            if(rs.next() == false){ // kullanıcı adı ve parolamız yoksa
                return false;
            }
            else{
                return true;
            }
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(Çalışanİşlemleri.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public ArrayList çalışanGetir(){
        
        ArrayList<Çalışan> çalışanlar = new ArrayList<Çalışan>();
        
        String sorgu = "Select * From calisanlar";
        
        try {
            
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            
            while(rs.next()){
                çalışanlar.add(new Çalışan(rs.getInt("id"), rs.getString("ad"), rs.getString("soyad"), rs.getString("departman"), rs.getString("maas"))); // çalışan classından obje alır
            }
            return çalışanlar;
        }
        catch (SQLException ex) {
            Logger.getLogger(Çalışanİşlemleri.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }                       
        
    }
    
    public void çalışanEkle(String ad, String soyad, String departman, String maaş){
        
        String sorgu = "INSERT INTO calisanlar (ad,soyad,departman,maas) VALUES (?, ?, ?, ?)";                   
        
        try {
            preparedStatement = con.prepareStatement(sorgu);  
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maaş);
            preparedStatement.executeUpdate();
        } 
        catch (SQLException ex) {
            Logger.getLogger(ÇalışanlarEkranı.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void çalışanGüncelle(int id,String ad, String soyad, String departman, String maaş){
        
        String sorgu = "Update calisanlar Set ad=? , soyad=? , departman=? , maas=? where id=?";
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maaş);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Çalışanİşlemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void çalışanSil(int id){
        
        String sorgu = "Delete From calisanlar where id = ?";
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Çalışanİşlemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args){
        Çalışanİşlemleri x = new Çalışanİşlemleri();
    }
       
}
