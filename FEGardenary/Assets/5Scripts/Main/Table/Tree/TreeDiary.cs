using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Net.Http;
using System;
using System.Net;
using Newtonsoft.Json;
using System.Text;
using TMPro;

public class TreeDiary : MonoBehaviour
{
    //나무 다이어리의 기록을 보여줄 오브젝트
    GameObject treeContentPrefab;

    //우리 서비스 uri와 사용할 토큰(임의로 만든 토큰)
    private string uri = "https://k7a604.p.ssafy.io/api/";
    private string token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzMTMiLCJpYXQiOjE2Njc4NzQxMjEsImV4cCI6MTY2Nzg5MjEyMX0.GPATAoMzVD5tTTe5NXYcnpddxMHsEPE3nqVm9Dp68V4";
    
    //api 요청 후 response 결과를 받을 클래스
    public ResTreeDiary TreeDiaryList;

    void Start()
    {
        //나무 다이어리 조회 API
        //요청을 보내기 위한 client
        var client = new HttpClient();

        //body로 넣을 데이터
        var dateCreate = new ReqTreeDiary
        {
            //유저가 선택한 날짜가 들어가도록 변경해야함(지금은 임의로 적은 것)
            date = "2022-11-08T09:54:01.242012"
        };
        var treeDate = JsonConvert.SerializeObject(dateCreate);
        var requestTreeDate = new StringContent(treeDate, Encoding.UTF8, "application/json");

        //Method와 Uri를 설정하고 Header에 토큰을 넣는다
        var httpRequestMessage = new HttpRequestMessage
        {
            Method = HttpMethod.Post,
            RequestUri = new Uri(uri + "tree/diary/date"),
            Headers =
            {
                { HttpRequestHeader.Authorization.ToString(), "Bearer " + token }
            },
        };

        //위의 body로 넣을 데이터를 같이 담아준다
        httpRequestMessage.Content = requestTreeDate;
       
        //API 요청을 실행하고 json으로 결과를 받아온다
        var response = client.SendAsync(httpRequestMessage).Result;
        var json = response.Content.ReadAsStringAsync().Result;
        //json으로 받아온 결과를 string으로 바꾼다(?)
        //데이터를 받을 클래스를 하나 만들어야 한다
        TreeDiaryList = JsonConvert.DeserializeObject<ResTreeDiary>(json);
        Debug.Log(TreeDiaryList.responseDto.Count);

        CreateTreeContent();
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    //나무 다이어리의 특정 날짜의 기록을 보여주는 함수
    void CreateTreeContent()
    {
        //treeContentPrefab = Resources.Load("TreeContent") as GameObject;
        for(int i = 0; i < TreeDiaryList.responseDto.Count; i++)
        {
            treeContentPrefab = Resources.Load("TreeContentPrefab") as GameObject;
            treeContentPrefab.transform.Find("TreeContentText").GetComponentInChildren<TextMeshProUGUI>().text
                = TreeDiaryList.responseDto[i].content;
            treeContentPrefab.transform.SetParent(GameObject.Find("TreeViewport").transform.Find("TreeContent"));
        }
    }
}
