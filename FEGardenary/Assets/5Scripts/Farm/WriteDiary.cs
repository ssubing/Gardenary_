using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class WriteDiary : MonoBehaviour
{
    //꽃 경험치, 나무 경험치, 오늘 꽃 작성 여부, 질문 내용, 몇 번째 질문, 꽃 연속 날짜, 나무 연속 날짜
    public int flowerExp;
    public int treeExp;
    private bool flowerFlag;
    public string questionContent;
    public int questionNum;
    public int flowerNum;
    public int treeNum;

    private GameObject targetObject;

    // Start is called before the first frame update
    void Start()
    {
        //텃밭 조회 API 실행
        flowerExp = 10;
        treeExp = 20;
        flowerFlag = true;
        questionContent = "오늘 하루 가장 기억에 남는 일은 무엇인가요?";
        questionNum = 14;
        flowerNum = 5;
        treeNum = 7;

        //경험치에 따라 다른 꽃을 보여줘야 한다
        if (flowerExp <= 20)
        {
            GameObject.Find("FlowerGroup").transform.Find("Flower1").gameObject.SetActive(true);
        }
        else if(flowerExp <= 40)
        {
            GameObject.Find("FlowerGroup").transform.Find("Flower2").gameObject.SetActive(true);
        }
        else if(flowerExp <= 70)
        {

        }
        else if(flowerExp < 100)
        {

        }
        
        //경험치에 따라 다른 나무를 보여줘야 한다
        if(treeExp <= 20)
        {
            GameObject.Find("TreeGroup").transform.Find("Tree1").gameObject.SetActive(true);
        }
        else if (treeExp <= 40)
        {
            
        }
        else if (treeExp <= 70)
        {

        }
        else if (treeExp < 100)
        {

        }
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            targetObject = GetClickedObject();
            //클릭한 오브젝트가 꽃일 때
            if(targetObject.name == "Flower1" || targetObject.name == "Flower2" ||
                targetObject.name == "Flower3" || targetObject.name == "Flower4")
            {
                //아직 오늘의 꽃 다이어리를 작성하지 않았을 때
                if(flowerFlag == true)
                {
                    //꽃 다이어리 작성 UI를 보여줘!
                    GameObject.Find("FlowerUI").transform.Find("FlowerWrite").gameObject.SetActive(true);
                }
                //이미 작성했을 때
                else
                {
                    //이미 작성했다는 UI를 보여줘!
                    GameObject.Find("FarmUI").transform.Find("AlreadyWrite").gameObject.SetActive(true);
                }
            }
            //클릭한 오브젝트가 나무일 때
            else if(targetObject.name == "Tree1" || targetObject.name == "Tree2" ||
                targetObject.name == "Tree3" || targetObject.name == "Tree4")
            {
                //나무 다이어리 작성 UI를 보여줘!
                GameObject.Find("TreeUI").transform.Find("TreeWrite").gameObject.SetActive(true);
            }
        }
    }

    //클릭한 오브젝트가 어떤 것
    GameObject GetClickedObject()
    {

        RaycastHit hit; // 충돌이 감지된 영역
        GameObject target = null;

        Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);    // 마우스 포인트 근처 좌표 생성

        // 마우스 근처에 오브젝트가 있는지 확인
        if (Physics.Raycast(ray.origin, ray.direction * 10, out hit))
        {
            target = hit.collider.gameObject;   // 해당 오브젝트 저장
        }

        return target;
    }
}
