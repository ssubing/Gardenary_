using UnityEngine;

public class KakaoLogin : MonoBehaviour
{
    private AndroidJavaObject _androidJavaObject;

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

    // 朝朝神ID 鋼発
    public void getKakaoId(string kakaoId)
    {
        Debug.Log("kakaoId : " + kakaoId);

    }

}
