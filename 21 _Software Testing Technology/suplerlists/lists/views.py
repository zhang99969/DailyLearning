from django.http import HttpResponse
from django.shortcuts import render
def home_page(request):
    #return HttpResponse('<html><title>To-Do lists</title></html>')
  
 #   if request.method == 'POST':
 #       return HttpResponse(request.POST['item_text'])
 #   return render(request, 'home.html')
    return render(request, 'home.html', {
            'new_item_text': request.POST.get('item_text', ''),
    })