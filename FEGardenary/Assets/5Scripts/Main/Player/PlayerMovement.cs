using UnityEngine;
using UnityEngine.SceneManagement;

public class PlayerMovement : MonoBehaviour
{
    GameObject nearObject;
    private GameObject targetObject;

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

        // 책상으로 카메라 줌인
        if (Input.GetMouseButtonDown(0))
        {
            //Debug.Log("?");
            targetObject = GetClickedObject();

            if (targetObject != null)
            {
                //Debug.Log(targetObject.name);
            }
        }
    }

    // 클릭한 오브젝트
    GameObject GetClickedObject()
    {

        RaycastHit hit; // 충돌이 감지된 영역
        GameObject target = null;

        Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);    // 마우스 포인트 근처 좌표 생성

        // 마우스 근처에 오브젝트가 있는지 확인
        if (Physics.Raycast(ray.origin, ray.direction * 10, out hit))
        {
            target = hit.collider.gameObject;   // 해당 오브젝트 저장
        }

        return target;
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
