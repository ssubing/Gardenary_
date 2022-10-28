using UnityEngine;
using UnityEngine.SceneManagement;

public class PlayerMovement : MonoBehaviour
{
    GameObject nearObject;
    static public PlayerMovement player;
    // Scene 전환 이후에도 Player가 남아있도록
    private void Awake()
    {
        if (player == null)
        {
            player = this;
            DontDestroyOnLoad(gameObject);

        }
        else
        {
            Destroy(this.gameObject);

        }

    }

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

        rb.velocity = new Vector3(JoyStickMovement.Instance.joyVec.x * moveSpeed, rb.velocity.y, JoyStickMovement.Instance.joyVec.y * moveSpeed);
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
    }
}
