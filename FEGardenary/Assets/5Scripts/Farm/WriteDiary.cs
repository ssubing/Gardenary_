using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class WriteDiary : MonoBehaviour
{
    public InputField flowerInput;
    //public string flowerText;
    // Start is called before the first frame update
    void Start()
    {
        //flowerText = flowerInput.GetComponent<InputField>().text;
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            GameObject.Find("Canvas").transform.Find("Panel").gameObject.SetActive(true);
        }
    }
    public void Close()
    {
        GameObject.Find("Canvas").transform.Find("Panel").gameObject.SetActive(false);
    }

    public void Write()
    {
        if(flowerInput.text.Length != 0)
        {
            Debug.Log("작성했다");
            Debug.Log(flowerInput.text);
        }
        //flowerText = flowerInput.text;
        Debug.Log("무야호");
        //Debug.Log(flowerText);
    }
}
