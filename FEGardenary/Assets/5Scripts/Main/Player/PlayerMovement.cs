using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMovement : MonoBehaviour
{
    public static PlayerMovement Instance
    {
        get
        {
            if (instance == null)
            {
                instance = FindObjectOfType<PlayerMovement>();
                if (instance == null)
                {
                    var instanceContainer = new GameObject("PlayerMovement");
                    instance = instanceContainer.AddComponent<PlayerMovement>();
                }
            }
            return instance;
        }
    }

    private static PlayerMovement instance;

    Rigidbody rb;
    public float moveSpeed = 25f;

    // Start is called before the first frame update
    void Start()
    {
        rb = GetComponent<Rigidbody>();
    }

    void FixedUpdate()
    {
        float moveHorizontal = Input.GetAxis("Horizontal");
        float moveVertical = Input.GetAxis("Vertical");

        //Debug.Log("")

        rb.velocity = new Vector3(moveHorizontal * moveSpeed, rb.velocity.y, moveVertical * moveSpeed);

        rb.velocity = new Vector3(JoyStickMovement.Instance.joyVec.x * moveSpeed, rb.velocity.y, JoyStickMovement.Instance.joyVec.y * moveSpeed);

        //Debug.Log(JoyStickMovement.Instance.joyVec.x + ", " + JoyStickMovement.Instance.joyVec.y + ", " + JoyStickMovement.Instance.joyVec.z);
    }
}
