import communityTree from '../assets/images/communityTree.png'

export default function MemoryItem() {
  return (
    // 데이터 받아온 후 다른 가족의 나무보기 페이지로 보내는 Link 추가
    // 이미지, 가족명, 가족아이디, 자랑하기 내용 받아오기
    <div className="p-5 snap-center">
      <div className="bg-white shadow-md border border-gray-200 rounded-lg max-w-sm dark:bg-gray-800 dark:border-gray-700 p-3">
        <img className="rounded-lg" src={communityTree} alt="tree" />
        <h3 className="font-bold text-lg mt-2">춘식라이언 가족 나무</h3>
        <p className="font-normal text-gray-700 mb-3 dark:text-gray-400">우리 가족 나무입니다.</p>
      </div>
    </div>
  )
}