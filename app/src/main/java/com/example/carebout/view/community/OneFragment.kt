package com.example.carebout.view.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.carebout.databinding.FragmentOneBinding

class OneFragment : Fragment() {
    lateinit var adapter: MyAdapter
    lateinit var filePath: String
    var contents: MutableList<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        /*
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        binding.editButton.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            startActivity(intent)
        }
        binding.galleryButton.setOnClickListener {
            val intent = Intent(context, ImageActivity::class.java)
            startActivity(intent)
        }

        return inflater.inflate(R.layout.fragment_one, container, false)
         */

        val binding = FragmentOneBinding.inflate(inflater, container, false)

        // 사용자가 입력한 텍스트를 가져옴
        val userEnteredText = arguments?.getString("userEnteredText")

        // contents를 초기화하고 userEnteredText를 추가
        contents = mutableListOf<String>().apply {
            userEnteredText?.let { add(it) }
        }


        //리사이클러 뷰를 위한 데이터 준비
        contents?.addAll(listOf("오늘도 산책 완료"))
        val contents_sub = mutableListOf<String>("귀여운 내 꾸미", "야옹야옹", "밥풀이네")
        val contents_date = mutableListOf<String>("신나게 놀고 난 후", "밥풀이네", "2022 9월")


        //리사이클러 뷰에 LayoutManger, Adapter, ItemDecoration 적용
        val columnCount = 2 // 그리드의 열 개수를 설정
        val layoutManager = GridLayoutManager(activity, columnCount)

        // 리사이클러 뷰 어댑터 초기화 시 Context 전달
        adapter = MyAdapter(requireContext(), contents!!, contents_sub, contents_date)

        // 리사이클러뷰 어댑터에 아이템 클릭 리스너 설정
        adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // 아이템 클릭 시 처리할 내용 작성
                // 다른 화면으로 전환
                if (position >= 0 && position < contents!!.size) {
                    val intent = Intent(context, StoryActivity::class.java)
                    intent.putExtra("data_key", contents!![position]) // 데이터 전달
                    startActivity(intent)
                } else {
                    Log.e("MyApp", "Invalid position: $position")
                }
            }
        })

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context))

        return binding.root
    }
}

    /*


        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            it.data!!.getStringExtra("result")?.let {
                contents?.add(it)
                adapter.notifyDataSetChanged()
            }
        }

        binding.editButton.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            requestLauncher.launch(intent)
        }

        // 갤러리 요청 런처
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            try {
                // inSampleSize 비율 계산, 지정
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize)
                )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                // 이미지 로딩
                var inputStream = contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream!!.close()
                inputStream = null

                bitmap?.let {
                    binding.userImageView.setImageBitmap(bitmap)
                } ?: let {
                    Log.d("kkang", "bitmap null")
                }
            }catch (e: Exception) {
                Log.d("kkang", "bitmap null")
            }
        }


        binding.galleryButton.setOnClickListener {
            //갤러리 앱
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }


    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true // 옵션만 설정하고자 true로 지정
        try {
            var inputStream = contentResolver.openInputStream(fileUri)
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight &&
                halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }


     */
     */
