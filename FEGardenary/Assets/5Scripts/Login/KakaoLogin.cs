using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Net.Http;
using System;
using System.Net;
using Newtonsoft.Json;
using System.Text;

public class KakaoLogin : MonoBehaviour
{
    private AndroidJavaObject _androidJavaObject;
    private string uri = "https://k7a604.p.ssafy.io/api/";

    public void Show()
    {
        Debug.Log("click");
    }

    void Start()
    {
        _androidJavaObject = new AndroidJavaObject("com.DefaultCompany.FEGardenary.UKakao");

    }

    public void Login()
    {
        _androidJavaObject.Call("KakaoLogin");
    }

    // 카카오 로그인 API
    public void getUserInfo(string kakaoId)
    {
        //Debug.Log("kakaoId : " + kakaoId);

        var client = new HttpClient();

        //body로 넣을 데이터
        var data = new KakaoId
        {
            kakaoId = kakaoId
        };
        var kakaoData = JsonConvert.SerializeObject(data);
        var requestKakaoId = new StringContent(kakaoData, Encoding.UTF8, "application/json");

        //Method와 Uri를 설정하고 Header에 토큰을 넣는다
        var httpRequestMessage = new HttpRequestMessage
        {
            Method = HttpMethod.Post,
            RequestUri = new Uri(uri + "user/login"),
        };

        //위의 body로 넣을 데이터를 같이 담아준다
        httpRequestMessage.Content = requestKakaoId;

        //API 요청을 실행하고 json으로 결과를 받아온다
        var response = client.SendAsync(httpRequestMessage).Result;
        var json = response.Content.ReadAsStringAsync().Result;
        //json으로 받아온 결과를 string으로 바꾼다(?)
        //데이터를 받을 클래스를 하나 만들어야 한다
        ResKakao request = JsonConvert.DeserializeObject<ResKakao>(json);

        //Debug.Log("accessToken: " + request.responseDto.accessToken);
        //Debug.Log("nickname: " + request.responseDto.nickname);

        // 로그인 성공
        if(request.status == "OK")
        {
            GameObject.Find("Login Panel").SetActive(false);
        }


    }

}
