package au.edu.uts.wsd;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class TestJAXB {
    
    public static void main(String[] args) throws Exception {
        
        //System.out.println("ID: "+UUID.randomUUID());
        
        Path base = Paths.get("src", "main", "webapp", "WEB-INF");
        
        PetManager manager = new PetManager(base.toAbsolutePath().toString(), "/PetOwners.xml");
        
        manager.save();
        
    }
    
}
