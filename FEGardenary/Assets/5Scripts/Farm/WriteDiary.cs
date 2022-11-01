using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class WriteDiary : MonoBehaviour
{
    //오늘 꽃 다이어리를 작성했는 지의 여부
    private bool flowerFlag;

    // Start is called before the first frame update
    void Start()
    {
        flowerFlag = true;
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            Debug.Log(flowerFlag);
            if(flowerFlag == true)
            {
                Debug.Log("화장실 가고 싶다");
                GameObject.Find("FarmUI").transform.Find("FlowerWrite").gameObject.SetActive(true);
            }
            else
            {
                GameObject.Find("FarmUI").transform.Find("AlreadyWrite").gameObject.SetActive(true);
            }
        }
    }
    public void Close()
    {
        GameObject.Find("FarmUI").transform.Find("FlowerWrite").gameObject.SetActive(false);
    }

    public void AlreadyClose()
    {
        GameObject.Find("FarmUI").transform.Find("AlreadyWrite").gameObject.SetActive(false);
    }
}
