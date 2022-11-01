using TMPro;
using UnityEngine;

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

    //작성 후 경험치
    private int allExp;

    //아이템 획득 여부
    private bool itemFlag;

    //캐릭터 획득 여부
    private int characterFlag;

    // Start is called before the first frame update
    void Start()
    {
        //꽃 경험치, 질문 내용, 몇 번째 질문, 연속 며칠 되었는지에 대한 변수를 다른 스크립트에서 가져오기
        WriteDiary writeDiary = GameObject.Find("FlowerGroup").GetComponent<WriteDiary>();
        flowerExp = writeDiary.flowerExp;
        question = writeDiary.questionContent;
        questionNum = writeDiary.questionNum;
        flowerNum = writeDiary.flowerNum;

        //꽃 연속 며칠 작성?
        flowerPeriod = GameObject.Find("Header").transform.Find("Date").GetComponentInChildren<TextMeshProUGUI>();
        flowerPeriod.text = "연속 " + flowerNum + "일";

        //몇 번째 질문?
        questionNumUI = GameObject.Find("QuestionBox").transform.Find("QuestionNumber").GetComponentInChildren<TextMeshProUGUI>();
        questionNumUI.text = "# " + questionNum + "번째 질문";

        //질문 내용
        questionUI = GameObject.Find("QuestionBox").transform.Find("Question").GetComponentInChildren<TextMeshProUGUI>();
        questionUI.text = question;
    }

    // Update is called once per frame
    void Update()
    {

    }

    //작성 버튼 클릭
    public void Write()
    {
        //작성한 답변
        flowerAnswer = flowerInput.text;
        //작성한 내용이 없을 때
        if (flowerAnswer.Length == 0)
        {
            //작성 완료 UI 표시
            GameObject.Find("FarmUI").transform.Find("WriteComplete").gameObject.SetActive(true);
            GameObject.Find("WriteComplete").transform.Find("WritePopup").gameObject.SetActive(true);
            //글자를 작성해달라는 문구 출력
            GameObject.Find("WritePopup").transform.Find("PopupText").GetComponentInChildren<TextMeshProUGUI>().text = "글자를 작성해주세요! :(";
        }
        //작성한 내용이 있을 때
        else if (flowerAnswer.Length != 0)
        {
            //꽃 다이어리 작성 API 호출
            //작성 후 총 경험치와 아이템 획득 여부를 받음
            allExp = 100;
            itemFlag = true;

            //작성 완료 UI 표시
            GameObject.Find("FarmUI").transform.Find("WriteComplete").gameObject.SetActive(true);
            GameObject.Find("WriteComplete").transform.Find("WritePopup").gameObject.SetActive(true);
            //작성 완료 문구 출력
            GameObject.Find("WritePopup").transform.Find("PopupText").GetComponentInChildren<TextMeshProUGUI>().text = "작성이 완료되었습니다! :)";
        }
    }

    //아이템 획득
    public void GetItem()
    {
        //아이템 획득 API 호출 -> 아이템 에셋 아이디, 이름
        GameObject.Find("FarmUI").transform.Find("GetItem").gameObject.SetActive(true);
        //완성했을 경우 캐릭터 API 호출
        if (allExp == 100)
        {
            //캐릭터 아이디가 몇이냐
            characterFlag = 1;
            //반환된 캐릭터 아이디 값이 0일 때만 새로운 캐릭터를 얻지 않은 것
            if (characterFlag != 0)
            {
                //새로운 캐릭터를 얻었다는 UI 표시
                GameObject.Find("FarmUI").transform.Find("GetCharacter").gameObject.SetActive(true);
            }
        }
    }

    public void Close()
    {
        //작성했을 경우 텃밭으로 돌아가기
        if (flowerAnswer.Length != 0)
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
