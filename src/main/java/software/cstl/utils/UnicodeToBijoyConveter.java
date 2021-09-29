package software.cstl.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UnicodeToBijoyConveter {
    Map<String, String> ar;
    Map<String, String> aM;
    Map<String, String> aR;
    Map<String, String> aS;

    public UnicodeToBijoyConveter() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        this.ar = new ObjectMapper().readValue(new File(classLoader.getResource("font-converter/ar.json").getFile()), HashMap.class);
        this.aR= new ObjectMapper().readValue(new File(classLoader.getResource("font-converter/aRR.json").getFile()), HashMap.class);
        this.aM = new ObjectMapper().readValue(new File(classLoader.getResource("font-converter/aM.json").getFile()), HashMap.class);
        this.aS = new ObjectMapper().readValue(new File(classLoader.getResource("font-converter/aS.json").getFile()), HashMap.class);
    }


    // begin --------

    String ci(String w)
    {
        var cY = 0;
        for (var i = 0; i < w.length(); i++)
        {
            if (i < w.length() && ao(w.charAt(i)))
            {
                var j = 1;
                while (v(w.charAt(i - j)))
                {
                    if (i - j < 0) break;
                    if (i - j <= cY) break;
                    if (D(w.charAt(i - j - 1))) j += 2;
                    else break;
                }
                var R = w.substring(0, i - j);
                R += w.charAt(i);
                R += w.substring(i - j, i);
                R += w.substring(i + 1, w.length());
                w = R;
                cY = i + 1;
                continue;
            }
            if (i < w.length() - 1 && D(w.charAt(i)) && w.charAt(i - 1) == 'র' && !D(w.charAt(i - 2)))
            {
                var j = 1;
                var aZ = 0;
                while (true)
                {
                    if (v(w.charAt(i + j)) && D(w.charAt(i + j + 1))) j += 2;
                    else if (v(w.charAt(i + j)) && ao(w.charAt(i + j + 1)))
                    {
                        aZ = 1;
                        break;
                    }
                    else break;
                }
                var R = w.substring(0, i - 1);
                R += w.substring(i + j + 1, i + j + aZ + 1);
                R += w.substring(i + 1, i + j + 1);
                R += w.charAt(i - 1);
                R += w.charAt(i);
                R += w.substring(i + j + aZ + 1, w.length());
                w = R;
                i += (j + aZ);
                cY = i + 1;
                continue;
            }
        }
        return w;
    }
    String fb = "0123456789ABCDEF";

    String fa(char d)
    {
        var h = fb.substring(d & 15, 1);
        while (d > 15)
        {
            d >>= 4;
            h = fb.substring(d & 15, 1) + h;
        }
        while (h.length() < 4) h = "0" + h;
        return h;
    }

    String bF(String line, boolean ef)
    {
        var text = "";
        for (int i = 0; i < line.length(); i++)
        {
            if (eu(line.charAt(i))) text += line.charAt(i);
            else text += "&#" + (ef ? String.valueOf(fa(line.charAt(i))) : String.valueOf(line.charAt(i))) + ";";
        }
        return text;
    }
    public String Convert(String line)
    {
        String az = "bangla";
        var G = ar;
        if (az == "bijoy") G = ar;
        else if (az == "somewherein") G = aM;
        else if (az == "boisakhi") G = aR;
        else if (az == "bangsee") G = aS;
        else if (az == "bornosoft")
        {
            return line;
        }
        else if (az == "phonetic")
        {
            return line;
        }
        else if (az == "htmlsafehex")
            return bF(line, true);
        else if (az == "htmlsafedec")
            return bF(line, false);
        G=ar;

        line = line.replace("ো", "ো");
        line = line.replace("ৌ", "ৌ");
        line = ci(line);
        char[] lineChars = line.toCharArray();
        for(char lineChar: lineChars){
            System.out.println(String.valueOf(lineChar));
            if(G.containsKey(String.valueOf(lineChar))){
                System.out.println(G.get(String.valueOf(lineChar)));
            }
        }
        for(Map.Entry<String, String> entry: G.entrySet()){
            line = line.replace(entry.getKey(), entry.getValue());
        }

        return line;
    }

    boolean bA(char e)
    {
        if (e == '০' || e == '১' || e == '২' || e == '৩' || e == '৪' || e == '৫' || e == '৬' || e == '৭' || e == '৮' || e == '৯') return true;
        return false;
    }

    boolean ao(char e)
    {
        if (e == 'ি' || e == 'ৈ' || e == 'ে') return true;
        return false;
    }

    boolean aJ(char e)
    {
        if (e == 'া' || e == 'ো' || e == 'ৌ' || e == 'ৗ' || e == 'ু' || e == 'ূ' || e == 'ী' || e == 'ৃ') return true;
        return false;
    }

    boolean ah(char e)
    {
        if (ao(e) || aJ(e)) return true;
        return false;
    }

    boolean v(char e)
    {
        if (e == 'ক' || e == 'খ' || e == 'গ' || e == 'ঘ' || e == 'ঙ' || e == 'চ' || e == 'ছ' || e == 'জ' || e == 'ঝ' || e == 'ঞ' || e == 'ট' || e == 'ঠ' || e == 'ড' || e == 'ঢ' || e == 'ণ' || e == 'ত' || e == 'থ' || e == 'দ' || e == 'ধ' || e == 'ন' || e == 'প' || e == 'ফ' || e == 'ব' || e == 'ভ' || e == 'ম' || e == 'শ' || e == 'ষ' || e == 'স' || e == 'হ' || e == 'য' || e == 'র' || e == 'ল' || e == 'য়' || e == 'ং' || e == 'ঃ' || e == 'ঁ' || e == 'ৎ') return true;
        return false;
    }

    boolean Q(char e)
    {
        if (e == 'অ' || e == 'আ' || e == 'ই' || e == 'ঈ' || e == 'উ' || e == 'ঊ' || e == 'ঋ' || e == 'ঌ' || e == 'এ' || e == 'ঐ' || e == 'ও' || e == 'ঔ') return true;
        return false;
    }

    boolean aF(char e)
    {
        if (e == 'ং' || e == 'ঃ' || e == 'ঁ') return true;
        return false;
    }

    boolean bS(String e)
    {
        if (e == "্য" || e == "্র") return true;
        return false;
    }

    boolean D(char e)
    {
        if (e == '্') return true;
        return false;
    }

    boolean eT(char e)
    {
        if (bA(e) || ah(e) || v(e) || Q(e) || aF(e) || bS(String.valueOf(e)) || D(e)) return true;
        return false;
    }

    boolean eu(char e)
    {
        if (e >= 0 && e < 128) return true;
        return false;
    }

    boolean cy(String e)
    {
        if (e == " " || e == "	" || e == "\n" || e == "\r") return true;
        return false;
    }

    String cJ(String e)
    {
        var t = "";
        if (e == "া") t = "আ";
        else if (e == "ি") t = "ই";
        else if (e == "ী") t = "ঈ";
        else if (e == "ু") t = "উ";
        else if (e == "ূ") t = "ঊ";
        else if (e == "ৃ") t = "ঋ";
        else if (e == "ে") t = "এ";
        else if (e == "ৈ") t = "ঐ";
        else if (e == "ো") t = "ও";
        else if (e == "ো") t = "ও";
        else if (e == "ৌ") t = "ঔ";
        else if (e == "ৌ") t = "ঔ";
        return t;
    }

    String bc(String e)
    {
        var t = "";
        if (e == "আ") t = "া";
        else if (e == "ই") t = "ি";
        else if (e == "ঈ") t = "ী";
        else if (e == "উ") t = "ু";
        else if (e == "ঊ") t = "ূ";
        else if (e == "ঋ") t = "ৃ";
        else if (e == "এ") t = "ে";
        else if (e == "ঐ") t = "ৈ";
        else if (e == "ও") t = "ো";
        else if (e == "ঔ") t = "ৌ";
        return t;
    }

    // end -------


    public static class Ext
    {
        public static String substring(String str, int from, int to)
        {
            return str.substring(from, to - from);
        }

        public static String substr(String str, int start, int length)
        {
            return str.substring(start, length);
        }
        public static String replace(String str, String pattern, String replacement)
        {
            return str.replaceAll(pattern, replacement);// Regex.Replace(str, pattern, replacement);
        }
        public static int charCodeAt(String str, int index)
        {
            return str.indexOf(index);// str.charAt(index);
        }
    }
}


