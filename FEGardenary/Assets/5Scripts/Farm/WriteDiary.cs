using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class WriteDiary : MonoBehaviour
{

    // Start is called before the first frame update
    void Start()
    {
        Debug.Log(GameObject.Find("Canvas"));
        //flowerText = flowerInput.GetComponent<InputField>().text;
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            GameObject.Find("Canvas").transform.Find("FlowerWrite").gameObject.SetActive(true);
        }
    }
    public void Close()
    {
        GameObject.Find("Canvas").transform.Find("FlowerWrite").gameObject.SetActive(false);
    }

}
