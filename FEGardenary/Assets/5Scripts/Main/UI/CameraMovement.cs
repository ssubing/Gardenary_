using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraMovement : MonoBehaviour
{
    public GameObject Player;
    private GameObject targetObject;

    public float offsetX;
    public float offsetY = 45f;
    public float offsetZ = -40f;

    public Transform target;    // 줌 타겟
    public float zoom;  // 줌 수치
    // public float zoomSpeed;
    public bool zoomFlag;

    Vector3 cameraPosition;

    static public CameraMovement camera;

    private void Awake()
    {
        if (camera == null)
        {
            camera = this;
            DontDestroyOnLoad(gameObject);

        }
        else
        {
            Destroy(this.gameObject);

        }

    }

    void LateUpdate()
    {
        // 책상 줌인, 줌아웃
        if(zoomFlag)
        {
            cameraPosition.x = -7.47f;
            cameraPosition.y = 8.63f;
            cameraPosition.z = -20.5f;

            transform.position = cameraPosition;
            transform.rotation = Quaternion.Euler(48.26f, 0, 0);
        } else
        {
            cameraPosition.x = Player.transform.position.x + offsetX;
            cameraPosition.y = Player.transform.position.y + offsetY;
            cameraPosition.z = Player.transform.position.z + offsetZ;

            transform.position = cameraPosition;
            transform.rotation = Quaternion.Euler(26.346f, 0, 0);
        }

        // 책상으로 카메라 줌인
        if (Input.GetMouseButtonDown(0))
        {
            targetObject = GetClickedObject();

            if (targetObject != null && targetObject.name == "Table")
            {
                zoomFlag = true;
                
            } else if(targetObject != null && targetObject.name == "Floor")
            {
                zoomFlag = false;
            }
        }
    }

    // 클릭한 오브젝트
    public GameObject GetClickedObject()
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


}
