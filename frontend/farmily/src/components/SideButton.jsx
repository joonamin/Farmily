export default function SideButton(category) {
  return (
    <p>
      <button className="px-5 py-2 text-lg rounded-md hover:bg-gray-200">
        {category.categoryName}
        {/* 버튼 */}
      </button>
    </p>
  )
}