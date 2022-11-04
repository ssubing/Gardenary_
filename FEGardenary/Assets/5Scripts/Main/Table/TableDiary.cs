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

    public TreeInfo treeDiary;
    // Start is called before the first frame update
    void Start()
    {
        //url
        treeDiary = new TreeInfo();
        treeDiary.status = "OK";
        treeDiary.message = "success";

        GetTreeAll treeInfo1 = new GetTreeAll();
        treeInfo1.content = "오늘 점심은 부산식 간짜장밥";
        //treeInfo1.diaryDate = DateTimeOffset.Now.LocalDateTime;

        GetTreeAll treeInfo2 = new GetTreeAll();
        treeInfo2.content = "커피는 왕 큰 커피";
        //treeInfo2.diaryDate = DateTimeOffset.Now.LocalDateTime;

        List<GetTreeAll> test = new List<GetTreeAll>();
        test.Add(treeInfo1);
        test.Add(treeInfo2);

        treeDiary.responseDto = test;
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
                    if(treeDiary.responseDto.Count == 0)
                    {
                        //작성되지 않았다는 UI를 보여준다
                    }
                    //그 날 작성한 일기가 있을 때
                    else
                    {
                        //개수만큼 UI를 만들어서 보여준다
                        for (int i = 0; i < treeDiary.responseDto.Count; i++)
                        {
                            //object를 하나 만들어준다
                            GameObject treeDiaryContent = new GameObject("TreeContent" + i);
                            //layer는 UI(5)로 설정
                            treeDiaryContent.layer = 5;
                            //부모 오브젝트 설정
                            treeDiaryContent.transform.SetParent(GameObject.Find("AllTreeUI").transform.Find("AllTreeBackground"));

                            //필요한 컴포넌트 추가
                            treeDiaryContent.AddComponent<CanvasRenderer>();
                            treeDiaryContent.AddComponent<RectTransform>();
                            treeDiaryContent.AddComponent<Image>();
                            //오브젝트의 위치 설정
                            treeDiaryContent.GetComponent<RectTransform>().anchoredPosition = new Vector2(0, -600 * i);
                            //오브젝트의 크기 설정
                            treeDiaryContent.GetComponent<RectTransform>().sizeDelta = new Vector2(1200, 400);

                            //테스트를 위해 임시로 색 설정
                            treeDiaryContent.GetComponent<Image>().color = Color.red;

                            //텍스트 추가
                            GameObject treeText = new GameObject("treeText" + i);
                            treeText.layer = 5;
                            treeText.transform.SetParent(GameObject.Find("AllTreeBackground").transform.Find("TreeContent" + i));

                            treeText.AddComponent<CanvasRenderer>();
                            treeText.AddComponent<RectTransform>();
                            treeText.AddComponent<TextMeshProUGUI>();

                            //글씨 크기와 폰트 설정
                            treeText.GetComponent<TextMeshProUGUI>().fontSize = 60;
                            treeText.GetComponent<TextMeshProUGUI>().font
                                = GameObject.Find("AllTreeHeaderText").GetComponent<TextMeshProUGUI>().font;

                            //내용 설정
                            treeText.GetComponent<TextMeshProUGUI>().text = treeDiary.responseDto[i].content;

                            treeText.GetComponent<RectTransform>().anchoredPosition = new Vector2(0, 0);
                        }
                    }
                }
            }
        }
    }
}
