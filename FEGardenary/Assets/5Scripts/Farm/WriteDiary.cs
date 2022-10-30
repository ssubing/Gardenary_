using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class WriteDiary : MonoBehaviour
{
    public bool state;
    private GameObject ui;
    // Start is called before the first frame update
    void Start()
    {
        state = false;
        ui = GameObject.Find("Panel");
        ui.SetActive(false);
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            Debug.Log("킨다");
            //화면 정중앙에 오게 함
            // uiGroup.anchoredPosition = Vector3.zero;
            Debug.Log(state);
            if(state == false)
            {
                Debug.Log("state 바뀌나?");
                ui.SetActive(true);
                state = true;
                Debug.Log(state);
            }
        }
    }
    public void Close()
    {
        if(state == true)
        {
            ui.SetActive(false);
            state = false;
        }
    }
}
