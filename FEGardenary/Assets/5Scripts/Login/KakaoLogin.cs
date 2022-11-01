using System.Collections;
using UnityEngine;
using UnityEngine.Networking;

public class KakaoLogin : MonoBehaviour
{
    private const string HOST = "https://kauth.kakao.com";
    private const string REST_API_KEY = "bcab0643aa89ef2c6aeea45bc2c46f00";
    private const string REDIRECT_URI = "https://getpostman.com/oauth2/callback";

    public void GenerateRequest()
    {
        string URI = HOST + "/oauth/authorize?client_id=" + REST_API_KEY + "&redirect_uri=" + REDIRECT_URI + "&response_type=code";
        //StartCoroutine(ProcessRequest(URI));
        Application.OpenURL("http://unity3d.com/");
    }

    private IEnumerator ProcessRequest(string uri)
    {
        using (UnityWebRequest request = UnityWebRequest.Get(uri))
        {
            yield return request.SendWebRequest();

            if (request.result == UnityWebRequest.Result.ConnectionError)
            {
                Debug.Log(request.error);
            }
            else
            {
   
                Debug.Log(request.result);
            }
        }
    }

}
