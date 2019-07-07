package top.dadagum.extractor.utils;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description TODO
 * @Author Honda
 * @Date 2019/7/7 10:01
 **/
public class NlpUtil {

    /**
     * 从文本中提取短语，默认提取数量为 10 个
     * @param text
     * @return
     */
    public static List<String> createShortWord(String text) {
        return createShortWord(text, 15);
    }

    public static List<String> createShortWord(String text, int cnt) {
        return HanLP.extractKeyword(text, cnt);
    }

    /**
     * 命名实体识别，本业务场景应该比较精确，想要更加精确的参考：
     * https://github.com/hankcs/HanLP/wiki/%E7%BB%93%E6%9E%84%E5%8C%96%E6%84%9F%E7%9F%A5%E6%9C%BA%E6%A0%87%E6%B3%A8%E6%A1%86%E6%9E%B6
     * @param text
     * @return
     */
    public static List<Term> NER(String text) {
        Segment segment = HanLP.newSegment()
                .enableOrganizationRecognize(true)
                .enablePlaceRecognize(true)
                .enableNameRecognize(true)
                .enableTranslatedNameRecognize(true);
        return segment.seg(text);
    }

    /**
     * 对词汇列表进行词性判别
     * @param words
     * @return
     */
    public static List<Term> NER(String[] words) {
        List<Term> res = new ArrayList<>(words.length);
        for (String w : words) {
            res.addAll(NER(w));
        }
        return res;
    }

    /**
     * 对于词语列表，只留下某些特定词性的词语（例如人名，机构名），其余去掉
     * @param set 指定留下的 Nature 列表
     * @return
     */
    public static List<Term> keepNatures(Set<String> set, List<Term> terms) {
        List<Term> res = new ArrayList<>();
        for (Term t : terms) {
            if (set.contains(t.nature.toString())) {
                res.add(t);
            }
        }
        return res;
    }
}
