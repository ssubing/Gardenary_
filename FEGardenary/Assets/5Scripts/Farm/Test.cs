using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;
using System.Net;

public class Test : MonoBehaviour
{
    public string url = "http://k7a604.p.ssafy.io/api";
    // Start is called before the first frame update
    void Start()
    {
        HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
        request.Method = "GET";
        request.Headers.Add("Authorization", "BASIC SGVsbG8=");

        using (HttpWebResponse resp = (HttpWebResponse)request.GetResponse())
        {
            HttpStatusCode status = resp.StatusCode;
            Debug.Log(status);
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
