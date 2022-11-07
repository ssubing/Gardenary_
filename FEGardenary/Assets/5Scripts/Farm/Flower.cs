using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class Flower : MonoBehaviour
{
    //꽃, 나무 다이어리 작성의 연속된 기간 표시 UI
    public TextMeshProUGUI flowerPeriod, treePeriod;

    //꽃이 몇 번째 질문인지, 질문 내용 표시 UI
    public TextMeshProUGUI questionUI, questionNumUI;

    //꽃, 나무의 현재 경험치
    private int flowerExp, treeExp;

    //꽃, 나무 다이어리 작성의 연속된 기간
    private int flowerNum, treeNum;

    //꽃 질문이 몇번째인지
    private int questionNum;
    //꽃 질문이 무엇인지
    private string question;

    //꽃 다이어리 작성
    public TMP_InputField flowerInput;
    private string flowerAnswer;

    //나무 다이어리 작성
    public TMP_InputField treeInput;
    private string treeText;

    //완성된 꽃이 무엇인지
    private string flowerName;

    //꽃 다이어리 작성 후 경험치
    private int FlowerAllExp, TreeAllExp;

    //아이템 획득 여부
    private bool FlowerItemFlag, TreeItemFlag;

    //캐릭터 획득 여부
    private int characterFlag;

    private WriteDiary writeDiary;

    private Transform check;

    // Start is called before the first frame update
    void Start()
    {
        //꽃 경험치, 질문 내용, 몇 번째 질문, 연속 며칠 되었는지에 대한 변수를 다른 스크립트에서 가져오기
        writeDiary = GameObject.Find("FlowerGroup").GetComponent<WriteDiary>();
        flowerExp = writeDiary.flowerExp;
        question = writeDiary.questionContent;
        questionNum = writeDiary.questionNum;
        flowerNum = writeDiary.flowerNum;

        //꽃 연속 며칠 작성?
        flowerPeriod = GameObject.Find("FlowerHeader").transform.Find("FlowerDate").GetComponentInChildren<TextMeshProUGUI>();
        flowerPeriod.text = "연속 " + flowerNum + "일";

        //몇 번째 질문?
        questionNumUI = GameObject.Find("QuestionBox").transform.Find("QuestionNumber").GetComponentInChildren<TextMeshProUGUI>();
        questionNumUI.text = "# " + questionNum + "번째 질문";

        //질문 내용
        questionUI = GameObject.Find("QuestionBox").transform.Find("Question").GetComponentInChildren<TextMeshProUGUI>();
        questionUI.text = question;
        //나무의 경험치와 연속적으로 작성한 기간에 대한 값을 다른 스크립트에서 가져오기
        treeExp = writeDiary.treeExp;
        treeNum = writeDiary.treeNum;

        //나무 연속 며칠 작성?
        treePeriod = GameObject.Find("TreeHeader").transform.Find("TreeDate").GetComponentInChildren<TextMeshProUGUI>();
        treePeriod.text = "연속 " + treeNum + "일";
    }

    // Update is called once per frame
    void Update()
    {

    }

    //꽃 다이어리 작성 버튼 클릭
    public void FlowerWrite()
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
            FlowerAllExp = 100;
            FlowerItemFlag = true;

            if (FlowerAllExp == 100)
            {

                //꽃 완성 UI 표시
                GameObject.Find("FarmUI").transform.Find("WriteComplete").gameObject.SetActive(true);
                GameObject.Find("WriteComplete").transform.Find("CompletePopup").gameObject.SetActive(true);
                //아이템 획득과 캐릭터 획득 체크
                check = GameObject.Find("CompletePopup").transform.Find("CompleteButton");
            }
            else
            {
                //작성 완료 UI 표시
                GameObject.Find("FarmUI").transform.Find("WriteComplete").gameObject.SetActive(true);
                GameObject.Find("WriteComplete").transform.Find("WritePopup").gameObject.SetActive(true);
                //작성 완료 문구 출력
                GameObject.Find("WritePopup").transform.Find("PopupText").GetComponentInChildren<TextMeshProUGUI>().text = "작성이 완료되었습니다! :)";//아이템 획득과 캐릭터 획득 체크
                //아이템 획득과 캐릭터 획득 체크
                check = GameObject.Find("WritePopup").transform.Find("PopupExitButton");
            }
            
            check.GetComponent<Button>().onClick.AddListener(() => GetItem(FlowerItemFlag));
            check.GetComponent<Button>().onClick.AddListener(() => GetCharacter(FlowerAllExp));
        }
    }

    //나무 다이어리 작성 버튼 클릭
    public void TreeWrite()
    {
        //작성한 내용
        treeText = treeInput.text;
        //작성한 내용이 없을 때
        if (treeText.Length == 0)
        {
            //작성 완료 UI 표시
            GameObject.Find("FarmUI").transform.Find("WriteComplete").gameObject.SetActive(true);
            GameObject.Find("WriteComplete").transform.Find("WritePopup").gameObject.SetActive(true);
            //글자를 작성해달라는 문구 출력
            GameObject.Find("WritePopup").transform.Find("PopupText").GetComponentInChildren<TextMeshProUGUI>().text = "글자를 작성해주세요! :(";
        }
        //있을 때
        else
        {
            //나무 다이어리 작성 API 호출
            //작성 후 총 경험치와 아이템 획득 여부를 받음
            TreeAllExp = 100;
            TreeItemFlag = true;

            if (TreeAllExp == 100)
            {
                //나무 완성 UI 표시
                GameObject.Find("FarmUI").transform.Find("WriteComplete").gameObject.SetActive(true);
                GameObject.Find("WriteComplete").transform.Find("CompletePopup").gameObject.SetActive(true);
                //아이템 획득과 캐릭터 획득 체크
                check = GameObject.Find("CompletePopup").transform.Find("CompleteButton");
            }
            else if (TreeAllExp < 100)
            {
                //작성 완료 UI 표시
                GameObject.Find("FarmUI").transform.Find("WriteComplete").gameObject.SetActive(true);
                GameObject.Find("WriteComplete").transform.Find("WritePopup").gameObject.SetActive(true);
                //작성 완료 문구 출력
                GameObject.Find("WritePopup").transform.Find("PopupText").GetComponentInChildren<TextMeshProUGUI>().text = "작성이 완료되었습니다! :)";
                check = GameObject.Find("WritePopup").transform.Find("PopupExitButton");
            }
            //아이템 획득과 캐릭터 획득 체크
            check.GetComponent<Button>().onClick.AddListener(() => GetItem(TreeItemFlag));
            check.GetComponent<Button>().onClick.AddListener(() => GetCharacter(TreeAllExp));

            //나무 다이어리 입력 초기화
            treeInput.Select();
            treeInput.text= "";
        }
    }

    //아이템 획득
    public void GetItem(bool itemFlag)
    {
        if (itemFlag == true)
        {
            //아이템 획득 API 호출 -> 아이템 에셋 아이디, 이름
            GameObject.Find("FarmUI").transform.Find("GetItem").gameObject.SetActive(true);
        }
    }


    //캐릭터 획득
    public void GetCharacter(int allExp)
    {
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

}
