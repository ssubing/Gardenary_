using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class Flower : MonoBehaviour
{
    //꽃, 나무 작성 연속 기간 표시 UI
    public TextMeshProUGUI flowerPeriod, treePeriod;
    //몇 번째 질문인지, 질문 내용 표시 UI
    public TextMeshProUGUI questionUI, questionNumUI;

    //꽃의 현재 경험치
    private int flowerExp;

    //꽃, 나무 작성 연속 기간의 숫자
    private int flowerNum, treeNum;

    //꽃 질문이 몇번째인지
    private int questionNum;
    //꽃 질문이 무엇인지
    private string question;

    //답변 작성
    public TMP_InputField flowerInput;
    private string flowerAnswer;

    //완성된 꽃이 무엇인지
    private string flowerName;

    // Start is called before the first frame update
    void Start()
    {
        //꽃 연속 며칠 작성?
        flowerPeriod = GameObject.Find("Header").transform.Find("Date").GetComponentInChildren<TextMeshProUGUI>();
        flowerNum = 15;
        flowerPeriod.text = "연속 " + flowerNum + "일";

        //몇 번째 질문?
        questionNumUI = GameObject.Find("QuestionBox").transform.Find("QuestionNumber").GetComponentInChildren<TextMeshProUGUI>();
        questionNum = 10;
        questionNumUI.text = "# " + questionNum + "번째 질문";

        //질문 내용
        questionUI = GameObject.Find("QuestionBox").transform.Find("Question").GetComponentInChildren<TextMeshProUGUI>();
        question = "오늘 하루 가장 기억에 남는 일은 무엇인가요?";
        questionUI.text = question;

        //경험치
        flowerExp = 90;
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void Write()
    {
        flowerAnswer = flowerInput.text;
        //작성했고 아직 성장중일 때
        Debug.Log(flowerExp);
        flowerExp += 10;
        if (flowerExp < 100 && flowerAnswer.Length != 0)
        {
            GameObject.Find("Canvas").transform.Find("WriteComplete").gameObject.SetActive(true);
            GameObject.Find("WriteComplete").transform.Find("WritePopup").gameObject.SetActive(true);
            GameObject.Find("WritePopup").transform.Find("PopupText").GetComponentInChildren<TextMeshProUGUI>().text = "작성이 완료되었습니다! :)";
        }
        //아무 것도 작성하지 않았을 경우
        else if(flowerAnswer.Length == 0)
        {
            flowerExp -= 10;
            GameObject.Find("Canvas").transform.Find("WriteComplete").gameObject.SetActive(true);
            GameObject.Find("WriteComplete").transform.Find("WritePopup").gameObject.SetActive(true);
            GameObject.Find("WritePopup").transform.Find("PopupText").GetComponentInChildren<TextMeshProUGUI>().text = "글자를 작성해주세요! :(";
        }
        else
        {
            GameObject.Find("Canvas").transform.Find("WriteComplete").gameObject.SetActive(true);
            GameObject.Find("WriteComplete").transform.Find("CompletePopup").gameObject.SetActive(true);
            //꽃 이름
            flowerName = "해바라기";
            GameObject.Find("CompleteTextWrap").transform.Find("CompleteText").GetComponentInChildren<TextMeshProUGUI>().text
                = flowerName + "이(가) 활짝 피었어요!";
        }
    }

    public void Close()
    {
        //작성했을 경우 텃밭으로 돌아가기
        if(flowerAnswer.Length != 0)
        {
            //작성창과 완료창 모두 끄기
            GameObject.Find("Canvas").transform.Find("WriteComplete").gameObject.SetActive(false);
            GameObject.Find("Canvas").transform.Find("FlowerWrite").gameObject.SetActive(false);
        }
        //작성하지 않았을 경우 작성 화면으로 돌아가기
        else
        {
            //완료창만 끄기
            GameObject.Find("Canvas").transform.Find("WriteComplete").gameObject.SetActive(false);
        }
    }
    public void Complete()
    {
        //작성창과 완료창 모두 끄기
        GameObject.Find("Canvas").transform.Find("WriteComplete").gameObject.SetActive(false);
        GameObject.Find("Canvas").transform.Find("FlowerWrite").gameObject.SetActive(false);
    }
}
