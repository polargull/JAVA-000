import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

public class CustomClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Class<?> hello = new CustomClassLoader().findClass("Hello");
            Method method = hello.getMethod("hello");
            method.invoke(hello.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println(">>>load path:" + this.getClass().getResource("/").getPath());
        URL resource = this.getClass().getResource("/Hello.xlass");
        File f = new File(resource.getPath());
        int length = (int) f.length();
        byte[] bytes = new byte[length];
        try {
            new FileInputStream(f).read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return super.findClass(name);
        }
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
}