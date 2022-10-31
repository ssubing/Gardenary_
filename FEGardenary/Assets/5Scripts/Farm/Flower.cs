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

    //꽃, 나무 작성 연속 기간의 숫자
    private int flowerNum, treeNum;

    //꽃 질문이 몇번째인지
    private int questionNum;
    //꽃 질문이 무엇인지
    private string question;

    private GameObject test;

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
        question = "오늘 하루 기억에 남는 일은 무엇인가요?";
        questionUI.text = question;
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
