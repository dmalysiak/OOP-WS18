package solutions.exercise8;

import solutions.exercise8.crypto.CryptToolbox;
import solutions.exercise8.crypto.SubstitutionHelper;
import solutions.exercise8.crypto.Vigenere;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        String s = "Ocs  yif  jcs  Cvtirsfcv  scvsf  udscvsv  bfivzesocoatsv  Rif,  jcs  Oatstsfszijs  tcsoo,  jio  scvzcks  Deuid  cv  jsf" +
                "Msjcvi,  jio  yistfsvj  jsf  kivzsv  Viatx  ebbsv  yif.  So  yif  mivatmid  kivz  dssf,  mivatmid  oioosv  jfsc  ejsf  pcsf"+
                "Dsgxs jifcv.  Ysvv  so  irsf  pedd  yif,  im  tisgbckoxsv  zycoatsv  zysc  gvj  jfsc  Gtf  viatxo,  tesfxs  miv  lsjso  Yefx,"+
                "jio  jcs  ivjsfsv  Kisoxs  oikxsv,  gfj  uim  mcx  lsjsm cvo Ksonfisat";

        String s2 = "LWFCOSYJYWTWHR"+
                "YKUGKLHLLOMGMXLPYNABVJJLAWTCVGALUTQ"+
                "BUXLQUKOVZCLRBSNSETPRYPMFDTLEOXSSJILPFLGBULHIBJQBUXJL"+
                "BAQFJEYIWZQBRTOILLEWTWKMYKQOIBLIOFESITYBMLMRKVSEOTF"+
                "AZGDIHFUQYTBGBKMUVLPVBSNSETPRYOUFBAPGBKOVNTYITWUHM"+
                "DYYHKMPVGEAYFZKZGKELSGTMDYFYJQWKTAWYAZKFASIHXPOECT"+
                "YYKESELPVTMQFJIBRMWDSRCNWNVMJFGEEVDQUVCPGBKFSYTOM"+
                "YJVSKOAZIJQITWCSYDXWXPUKMLRFVXDMYKASKLHAYAXWTWHRY"+
                "LIOJMNPUMNSLCKMBJZWTWARYAZWTWZXYZQVZTYSBFQOEVZXQ"+
                "WUZZROINOMGEXJLNNQFXTZWYFTOSTEMWZTOSTUMWZFJVGNIM"+
                "KQBUPZWCUTBZROXBAQFSXUAYYQBUTZAYYQGUTYZIJYWIAPIALEC"+
                "ESLVHP"+
                "ISXTUHYKISXTZHYJNSITPXMZUBKTYQCJXWVVAMWZTOST"+
                "UMWZFJVGNIMKQBUPZWQADPVGLMNKJGVXALOFPSIIQEBJQBXTNIH"+
                "VUSJTTEMUTWETUOUWYDWTUMWZTOSTUMWZFJVGNIMKQBUPZ"+
                "WMAQLJTPXBMZRVGANUZDSEXOVYSDAVTUWWZUQBTUYGMZGQJ"+
                "GILKFCVGLROFPBRROICFQAAPOVBMZRVGABXWEYIXLKYKTOSTPG"+
                "BFUQYICILYQGJ"+
                "TUAUKPOJLPGBLUUJILMMLIWIHPRXFAQYWPILDMG"+
                "JIBRMPTSLILRUUTHUXLWYJMFDTLZIFTWVGHYMWUBVQVXMUTOW"+
                "IZGBAOYVCSEMKFIEHOIOLQBRROXRVUSJTOSYZXSEOBQYJLWKILV"+
                "HTDWEVLRBWGHVCHGBLISISLRQADRZTZIBSXZVCHYMWDRVMZXU"+
                "ZXIESZXYAZSIQLFYFXOJHLRMAQGFASIHMZGYDLVYFHCDGVXYFWS"+
                "ICIMMRGAJROA"+
                "UJLSEMOMGEQZYTBXYFMQYIDILVQBNXYHUXGSIHV"+
                "VAWZRRHZWCWZWVBHPMNQFXTZWYFPOJXZXTAABLCKBQADVRQ"+
                "LREWUBVPUKML";

        //CryptToolbox.hillclimbCrack(new SubstitutionHelper(), s,100000);
        CryptToolbox.vigenereHillClimbCrack(new Vigenere(), s2,8,3,9000);
    }
}
