using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using System.Net;
using System.IO;
using UnityEngine.Networking;
using System.Net.Http;
using System;

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

    public string uri = "https://k7a604.p.ssafy.io/api/";

    public GameObject targetObject;

    //테스트를 위한 토큰 담을 변수
    //나중에 카카오 로그인해서 받아오는 변수를 써야 한다
    public string token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxOCIsImlhdCI6MTY2NzU0OTI0NywiZXhwIjoxNjY3NTUxMDQ3fQ.DJahNxpkrHsDDL_-XR7034A_mAmBxx_9seZvjsJEFwc";

    // Start is called before the first frame update
    void Start()
    {
        //텃밭 조회 API
        var client = new HttpClient();
        //Method와 Uri를 설정하고 Header에 토큰을 넣는다
        var httpRequestMessage = new HttpRequestMessage
        {
            Method = HttpMethod.Get,
            RequestUri = new Uri(uri + "current"),
            Headers =
            {
                {HttpRequestHeader.Authorization.ToString(), "Bearer " + token}
            }
        };

        //API 요청을 실행하고 json으로 결과를 받아온다
        var response = client.SendAsync(httpRequestMessage).Result;
        var json = response.Content.ReadAsStringAsync().Result;
        Debug.Log(json);
        //json으로 받아온 결과를 string으로 바꾼다(?)
        FarmInfo test = JsonUtility.FromJson<FarmInfo>(json);

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
                else if(flowerFlag == false || GameObject.Find("FarmUI").transform.Find("FlowerUI").gameObject.activeSelf == false)
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
                if(GameObject.Find("FarmUI").transform.Find("TreeUI").gameObject.activeSelf == false)
                {
                    GameObject.Find("FarmUI").transform.Find("TreeUI").gameObject.SetActive(true);
                }
                GameObject.Find("TreeUI").transform.Find("TreeWrite").gameObject.SetActive(true);
            }
        }
    }

    void test()
    {

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
