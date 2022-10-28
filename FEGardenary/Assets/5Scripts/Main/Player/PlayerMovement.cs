using UnityEngine;
using UnityEngine.SceneManagement;

public class PlayerMovement : MonoBehaviour
{
    GameObject nearObject;
    
    public static PlayerMovement Instance   // singleton
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

    void Update()
    {
        // Scene 전환
        if (nearObject != null)
        {
            if (nearObject.tag == "Farm")
            {
                SceneManager.LoadScene("Farm");
            }
            else if (nearObject.tag == "Garden")
            {
                SceneManager.LoadScene("Garden");
            }
        }
    }

    void OnTriggerStay(Collider other)
    {
        // Scene 전환을 위한 오브젝트 감지
        if (other.tag == "Farm" || other.tag == "Garden")
        {
            nearObject = other.gameObject;
        }

        /*if (nearObject != null)
        {
            Debug.Log(nearObject.tag);
        }*/
    }
}
