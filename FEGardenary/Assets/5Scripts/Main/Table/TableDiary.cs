using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using UnityEngine.UI;
using TMPro;


public class TableDiary : MonoBehaviour
{
    private CameraMovement cameraMovement;
    private GameObject targetObject;

    //책상에 줌인이 된 상태인가?
    private bool zoomFlag;


    //URI와 토큰
    public string uri = "https://k7a604.p.ssafy.io/api/";
    public string token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxOCIsImlhdCI6MTY2NzU0OTI0NywiZXhwIjoxNjY3NTUxMDQ3fQ.DJahNxpkrHsDDL_-XR7034A_mAmBxx_9seZvjsJEFwc";

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            //CameraMovement 스크립트를 가져온다
            cameraMovement = GameObject.Find("Main Camera").GetComponent<CameraMovement>();
            //선택한 오브젝트가 무엇인지에 대한 함수
            targetObject = cameraMovement.GetClickedObject();
            //책상에 줌이 되었는 지에 대해 알기 위해 변수를 가져온다
            zoomFlag = cameraMovement.zoomFlag;

            //책상에 줌인이 되었을 때만
            if (zoomFlag == true)
            {
                //클릭한 다이어리가 꽃 다이어리인 경우
                if (targetObject.name == "Flower Diary")
                {
                    Debug.Log("꽃 다이어리");
                    //꽃 다이어리 전체 목록을 보여준다
                    GameObject.Find("TableUI").transform.Find("AllFlowerUI").gameObject.SetActive(true);
                                        
                }
                //클릭한 다이어리가 나무 다이어리인 경우
                else if (targetObject.name == "Tree Diary")
                {
                    //나무 다이어리의 제일 바깥 UI(Canvas)를 활성화
                    GameObject.Find("TableUI").transform.Find("AllTreeUI").gameObject.SetActive(true);
                    //작성된 나무 다이어리 개수에 따라 표시
                    //아무 것도 작성하지 않았을 때
                    //if(treeDiary.responseDto.Count == 0)
                    //{
                        //작성되지 않았다는 UI를 보여준다
                    //}
                    //그 날 작성한 일기가 있을 때
                    /*else
                    {
                        
                    }*/
                }
            }
        }
    }
}
