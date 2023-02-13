using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.UI;

public class TreeContentItem : MonoBehaviour
{
    GameObject treeContentPrefab;

    // Start is called before the first frame update
    void Start()
    {
        treeContentPrefab = Resources.Load("TreeContent") as GameObject;

    }

    // Update is called once per frame
    void Update()
    {
        
    }

	
}
